package pers.anshay.tmall.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import pers.anshay.tmall.pojo.User;
import pers.anshay.tmall.service.IUserService;

/**
 * JPARealm
 *
 * @author: Anshay
 * @date: 2019/1/3
 */
public class JPARealm extends AuthorizingRealm {

    @Autowired
    IUserService userService;

    /**
     * @param token token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // debug下看看token数据结构，对比尚硅谷的代码学习理解
        String userName = token.getPrincipal().toString();
//        这里也可以获取用户名
//        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
//        String username = upToken.getUsername();
        User user = userService.getByName(userName);
        String psdInDB = user.getPassword();
        String salt = user.getSalt();
        ByteSource credentialsSalt = ByteSource.Util.bytes(salt);
        // 参数：用户名-密码-加工后的盐-realm
        //另一种将前台传过来的密码和数据库里的密码比对来验证登录，要多一个参数
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName, psdInDB,
                credentialsSalt, getName());
        return authenticationInfo;
    }

    /**
     * 授权会被Shiro回调的方法
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

}
