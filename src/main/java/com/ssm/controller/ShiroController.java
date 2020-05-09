package com.ssm.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ShiroController {

	@RequestMapping("/login")
	public String myLogin(@RequestParam("name") String user, @RequestParam("password") String password) {
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(user, password);
			token.setRememberMe(true);
			try {
				currentUser.login(token);
			} catch (AuthenticationException ae) {
				System.out.println("login fail---" + ae);
			}
		}
		return "redirect:/index.jsp";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String myLogout(RedirectAttributes ra) {
		Subject currentUser = SecurityUtils.getSubject();
		Object principal = currentUser.getPrincipal();
		if (principal != null) {
			currentUser.logout();
			ra.addFlashAttribute("message", "已安全登出");
		}
		return "redirect:/login.jsp";
	}
}
