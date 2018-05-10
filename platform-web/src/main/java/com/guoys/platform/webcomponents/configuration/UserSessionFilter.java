package com.guoys.platform.webcomponents.configuration;

/**
 * Created by gys on 2018/1/7.
 */

import com.guoys.platform.webcomponents.constant.GlobalConstant;
import com.guoys.platform.webcomponents.entity.DO.User;
import com.guoys.platform.webcomponents.service.UserService;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 * remberme 登录后的filter, 将用户信息写入到session中
 */

public class UserSessionFilter  extends AccessControlFilter {


    private UserService userService;

    private Logger LOG = LoggerFactory.getLogger(getClass());


    public UserSessionFilter(UserService userService){
        this.userService = userService;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = null;
        try{
            subject = getSubject(request, response);
        }catch (Throwable t){}

        if (subject == null) {
            // 没有登录
            return false;
        }
        HttpSession session = WebUtils.toHttp(request).getSession();
        Object sessionUsername = session.getAttribute(GlobalConstant.SESSION_AUTH_LOGIN_USERNAME);
        if (sessionUsername == null) {
            String username = (String) getAvailablePrincipal(subject.getPrincipals());
            User _user = userService.findUserByUsername(username);
            if(_user == null){
                LOG.warn("{}对应的用户不存在", username);
                return false;
            }
            session.setAttribute(GlobalConstant.SESSION_AUTH_LOGIN_USERNAME, _user);
        }
        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return true;
    }

    protected Object getAvailablePrincipal(PrincipalCollection principals) {
        Object primary = null;
        if (!CollectionUtils.isEmpty(principals)) {
            Collection thisPrincipals = principals.fromRealm(getName());
            if (!CollectionUtils.isEmpty(thisPrincipals)) {
                primary = thisPrincipals.iterator().next();
            } else {
                //no principals attributed to this particular realm.  Fall back to the 'master' primary:
                primary = principals.getPrimaryPrincipal();
            }
        }

        return primary;
    }
}
