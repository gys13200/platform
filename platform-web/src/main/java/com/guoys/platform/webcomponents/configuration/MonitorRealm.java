package com.guoys.platform.webcomponents.configuration;


import com.guoys.platform.persistence.util.StringUtil;
import com.guoys.platform.webcomponents.entity.DO.Function;
import com.guoys.platform.webcomponents.entity.DO.Role;
import com.guoys.platform.webcomponents.entity.DO.User;
import com.guoys.platform.webcomponents.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义 校验规则
 * 
 * @author seawind
 * 
 */

@Component
public class MonitorRealm extends AuthorizingRealm {

    public MonitorRealm(HashedCredentialsMatcher hashedCredentialsMatcher, UserService userService) {
        this.setCredentialsMatcher(hashedCredentialsMatcher);
        this.userService = userService;
    }

    private UserService userService;


    @Override
    // 授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 查询当前用户具有 角色和权限
		String username = (String) super.getAvailablePrincipal(principalCollection);
		User user = userService.findUserByUsername(username);
		// TODO 如果是管理员， 查询所有角色 ，连同权限
		List<Role> roles = user.getRoles();
		for (Role role : roles) {
			// 将role 加入 返回授权info
			simpleAuthorizationInfo.addRole(role.getName());
			// 加角色对应权限
			List<Function> functions = role.getFunctions();
			for (Function function : functions) {
				// 将function 加入返回授权info
				simpleAuthorizationInfo.addStringPermission(function.getName());
			}
		}
        return simpleAuthorizationInfo;
    }


    @Override
    // 认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 根据用户名 去查询密码
        User bean = userService.findUserByUsername(token.getUsername());
        if (bean == null || StringUtil.isEmpty(bean.getPassword())) {
            return null;
        }
        String password = bean.getPassword();
        return new SimpleAuthenticationInfo(token.getUsername(), password.toCharArray(), getName());

    }

}
