package com.guoys.platform.persistence.extension;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 扩展点接口的标识。
 * <p />
 * 扩展点声明配置文件，格式修改。<br />
 * 以Protocol示例，配置文件META-INF/services/com.xxx.Protocol内容：<br />
 * 由<br/>
 * <pre><code>com.foo.XxxProtocol
com.foo.YyyProtocol</code></pre><br/>
 * 改成使用KV格式<br/>
 * <pre><code>xxx=com.foo.XxxProtocol
yyy=com.foo.YyyProtocol
 * </code></pre>
 *
 * @export
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI {

    /**
     * 缺省扩展点名。
     */
	String value() default "";

}