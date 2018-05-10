package com.guoys.platform.persistence.extension;

import com.guoys.platform.persistence.extension.support.SpringExtensionHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;




/**
 * 使用的扩展点获取。
 * <p>
 * <ul>
 * <li>自动注入关联扩展点。</li>
 * <li>自动Wrap上扩展点的Wrap类。</li>
 * <li>缺省获得的的扩展点是一个Adaptive Instance。
 * </ul>
 * 
 */
public class ExtensionLoader<T> {

	private static final String SERVICES_DIRECTORY = "META-INF/services/";

	private static final Pattern NAME_SEPARATOR = Pattern.compile("\\s*[,]+\\s*");

	private static final ConcurrentMap<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<Class<?>, ExtensionLoader<?>>();

	private static final ConcurrentMap<Class<?>, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<Class<?>, Object>();

	private final Class<?> type;

	private final ConcurrentMap<Class<?>, String> cachedNames = new ConcurrentHashMap<Class<?>, String>();

	private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<Map<String, Class<?>>>();

	private final ConcurrentMap<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<String, Holder<Object>>();

	private final Map<String, Activate> cachedActivates = new ConcurrentHashMap<String, Activate>();

	private String cachedDefaultName;

	private Map<String, IllegalStateException> exceptions = new ConcurrentHashMap<String, IllegalStateException>();
	
	private ClassLoader extensionClassLoader;

	private static <T> boolean withExtensionAnnotation(Class<T> type) {
		return type.isAnnotationPresent(SPI.class);
	}
	
	public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
		return getExtensionLoader(type,findClassLoader());
	}

	@SuppressWarnings("unchecked")
	public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type,ClassLoader extensionClassLoader) {
		if (type == null)
			throw new IllegalArgumentException("Extension type == null");
		if (!type.isInterface() && !Modifier.isAbstract(type.getModifiers())) {
			throw new IllegalArgumentException("Extension type(" + type + ") is not interface or not abstract!");
		}
		
		if (!withExtensionAnnotation(type)) {
			throw new IllegalArgumentException("Extension type(" + type + ") is not extension, because WITHOUT @" + SPI.class.getSimpleName() + " Annotation!");
		}

		ExtensionLoader<T> loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
		if (loader == null) {
			EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<T>(type,extensionClassLoader));
			loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
		}
		
		return loader;
	}

	private ExtensionLoader(Class<?> type,ClassLoader extensionClassLoader) {
		this.type = type;
		this.extensionClassLoader=extensionClassLoader;
	}

	public String getExtensionName(T extensionInstance) {
		return getExtensionName(extensionInstance.getClass());
	}

	public String getExtensionName(Class<?> extensionClass) {
		return cachedNames.get(extensionClass);
	}

	/**
	 * 返回已经加载的扩展点的名字。
	 * <p />
	 * 一般应该调用{@link #getSupportedExtensions()}方法获得扩展，这个方法会返回所有的扩展点。
	 * 
	 * @see #getSupportedExtensions()
	 */
	public Set<String> getLoadedExtensions() {
		return Collections.unmodifiableSet(new TreeSet<String>(cachedInstances.keySet()));
	}

	/**
	 * 返回指定名字的扩展。如果指定名字的扩展不存在，则抛异常 {@link IllegalStateException}.
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getExtension(String name) {
		if (name == null || name.length() == 0)
			throw new IllegalArgumentException("Extension name == null");
		if ("true".equals(name)) {
			return getDefaultExtension();
		}

		Holder<Object> holder = cachedInstances.get(name);
		if (holder == null) {
			cachedInstances.putIfAbsent(name, new Holder<Object>());
			holder = cachedInstances.get(name);
		}
		Object instance = holder.get();
		if (instance == null) {
			synchronized (holder) {
				instance = holder.get();
				if (instance == null) {
					instance = createExtension(name);
					SpringExtensionHelper.initAutowireFields(instance);
					holder.set(instance);
				}
			}
		}
		return (T) instance;
	}

	/**
	 * 返回缺省的扩展，如果没有设置则返回<code>null</code>。
	 */
	public T getDefaultExtension() {
		getExtensionClasses();
		Set<String> extensions=this.getSupportedExtensions();
		if(extensions.size()>0){
			cachedDefaultName=extensions.toArray(new String[extensions.size()])[0];
		}else{
			return null;
		}
		
		return getExtension(cachedDefaultName);
	}

	public boolean hasExtension(String name) {
		if (name == null || name.length() == 0)
			throw new IllegalArgumentException("Extension name == null");
		try {
			return getExtensionClass(name) != null;
		} catch (Throwable t) {
			return false;
		}
	}

	public Set<String> getSupportedExtensions() {
		Map<String, Class<?>> clazzes = getExtensionClasses();
		return Collections.unmodifiableSet(new TreeSet<String>(clazzes.keySet()));
	}

	/**
	 * 返回缺省的扩展点名，如果没有设置缺省则返回<code>null</code>。
	 */
	public String getDefaultExtensionName() {
		getExtensionClasses();
		return cachedDefaultName;
	}

	private IllegalStateException findException(String name) {
		for (Map.Entry<String, IllegalStateException> entry : exceptions.entrySet()) {
			if (entry.getKey().toLowerCase().contains(name.toLowerCase())) {
				return entry.getValue();
			}
		}
		StringBuilder buf = new StringBuilder("No such extension " + type.getName() + " by name " + name);

		int i = 1;
		for (Map.Entry<String, IllegalStateException> entry : exceptions.entrySet()) {
			if (i == 1) {
				buf.append(", possible causes: ");
			}

			buf.append("\r\n(");
			buf.append(i++);
			buf.append(") ");
			buf.append(entry.getKey());
			buf.append(":\r\n");
			buf.append(entry.getValue().getMessage());
		}
		return new IllegalStateException(buf.toString());
	}

	@SuppressWarnings("unchecked")
	private T createExtension(String name) {
		Class<?> clazz = getExtensionClasses().get(name);
		if (clazz == null) {
			throw findException(name);
		}
		try {
			T instance = (T) EXTENSION_INSTANCES.get(clazz);
			if (instance == null) {
				EXTENSION_INSTANCES.putIfAbsent(clazz, (T) clazz.newInstance());
				instance = (T) EXTENSION_INSTANCES.get(clazz);
			}

			return instance;
		} catch (Throwable t) {
			throw new IllegalStateException("Extension instance(name: " + name + ", class: " + type + ")  could not be instantiated: " + t.getMessage(), t);
		}
	}

	private Class<?> getExtensionClass(String name) {
		if (type == null)
			throw new IllegalArgumentException("Extension type == null");
		if (name == null)
			throw new IllegalArgumentException("Extension name == null");
		Class<?> clazz = getExtensionClasses().get(name);
		if (clazz == null)
			throw new IllegalStateException("No such extension \"" + name + "\" for " + type.getName() + "!");
		return clazz;
	}

	private Map<String, Class<?>> getExtensionClasses() {
		Map<String, Class<?>> classes = cachedClasses.get();
		if (classes == null) {
			synchronized (cachedClasses) {
				classes = cachedClasses.get();
				if (classes == null) {
					classes = loadExtensionClasses();
					cachedClasses.set(classes);
				}
			}
		}
		return classes;
	}

	// 此方法已经getExtensionClasses方法同步过。
	private Map<String, Class<?>> loadExtensionClasses() {
		final SPI defaultAnnotation = type.getAnnotation(SPI.class);
		if (defaultAnnotation != null) {
			String value = defaultAnnotation.value();
			if (value != null && (value = value.trim()).length() > 0) {
				String[] names = NAME_SEPARATOR.split(value);
				if (names.length > 1) {
					throw new IllegalStateException("more than 1 default extension name on extension " + type.getName() + ": " + Arrays.toString(names));
				}
				if (names.length == 1)
					cachedDefaultName = names[0];
			}
		}

		Map<String, Class<?>> extensionClasses = new HashMap<String, Class<?>>();
		loadFile(extensionClasses, SERVICES_DIRECTORY);
		return extensionClasses;
	}

	private void loadFile(Map<String, Class<?>> extensionClasses, String dir) {
		String fileName = dir + type.getName();
		try {
			Enumeration<java.net.URL> urls;
			ClassLoader classLoader = findClassLoader();
			if (classLoader != null) {
				urls = classLoader.getResources(fileName);
			} else {
				urls = ClassLoader.getSystemResources(fileName);
			}
			if (urls != null) {
				while (urls.hasMoreElements()) {
					java.net.URL url = urls.nextElement();
					try {
						BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
						try {
							String line = null;
							while ((line = reader.readLine()) != null) {
								final int ci = line.indexOf('#');
								if (ci >= 0)
									line = line.substring(0, ci);
								line = line.trim();
								if (line.length() > 0) {
									try {
										String name = line;
										int i = line.indexOf('=');
										if (i > 0) {
											name = line.substring(0, i).trim();
											line = line.substring(i + 1).trim();
										}

										if (line.length() > 0) {
											Class<?> clazz = Class.forName(line, true, extensionClassLoader);
											if (!type.isAssignableFrom(clazz)) {
												throw new IllegalStateException("Error when load extension class(interface: " + type + ", class line: "
														+ clazz.getName() + "), class " + clazz.getName() + "is not subtype of interface.");

											} else {
												clazz.getConstructor();
												String[] names = NAME_SEPARATOR.split(name);
												if (names != null && names.length > 0) {
													Activate activate = clazz.getAnnotation(Activate.class);
													if (activate != null) {
														cachedActivates.put(names[0], activate);
													}
													for (String n : names) {
														if (!cachedNames.containsKey(clazz)) {
															cachedNames.put(clazz, n);
														}
														Class<?> c = extensionClasses.get(n);
														if (c == null) {
															extensionClasses.put(n, clazz);
														} else if (c != clazz) {
															throw new IllegalStateException("Duplicate extension " + type.getName() + " name " + n + " on "
																	+ c.getName() + " and " + clazz.getName());
														}
													}
												}
											}
										}
									} catch (Throwable t) {
										t.printStackTrace();
										IllegalStateException e = new IllegalStateException("Failed to load extension class(interface: " + type
												+ ", class line: " + line + ") in " + url + ", cause: " + t.getMessage(), t);
										exceptions.put(line, e);
									}
								}

							} // end of while read lines
						} finally {
							reader.close();
						}
					} catch (Throwable t) {
						throw new ExtensionLoadException("Exception when load extension class(interface: " + type + ", class file: " + url + ") in " + url, t);
					}
				} // end of while urls
			}
		} catch (Throwable t) {
			throw new ExtensionLoadException("Exception when load extension class(interface: " + type + ", description file: " + fileName + ").", t);
		}
	}

	private static ClassLoader findClassLoader() {
		return ExtensionLoader.class.getClassLoader();
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[" + type.getName() + "]";
	}

}