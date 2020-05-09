package com.ssm.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssm.bean.Employee;
import com.ssm.service.EmployeeService;

public class MyShiro extends AuthorizingRealm {

	@Autowired
	EmployeeService eService;

	/**
	 * 登入驗證
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		System.out.println("my shiro realm 1");
		UsernamePasswordToken token = (UsernamePasswordToken) arg0;
		String username = token.getUsername();
		Employee user = eService.getEmpByName(username);
		if (null == user) {
			throw new UnknownAccountException("用戶不存在");
		}
		// 3). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
		String realmName = getName();
		// 4). 盐值.
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		return new SimpleAuthenticationInfo(user.getLastName(), user.getPassword(), credentialsSalt, realmName);
	}

	/**
	 * 授權認證
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		System.out.println("my shiro realm 2");
		// 获取登录时输入的用户名
		String loginName = (String) principalCollection.fromRealm(getName()).iterator().next();
		// 到数据库查是否有此对象
		Employee user = eService.getEmpByName(loginName);
		if (user != null) {
			// 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 用户的角色集合
			Set<String> roles = new HashSet<>();
			roles.add("user");
			if ("sony".equals(user.getLastName())) {
				roles.add("admin");
			}
			info.addRoles(roles);
			return info;
		}
		System.out.println(user);
		return null;
	}
}
