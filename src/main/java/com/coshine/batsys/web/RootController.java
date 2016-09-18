package com.coshine.batsys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coshine.batsys.context.BatsysProperties;

@Controller
public class RootController {
	
	@Autowired
	private BatsysProperties properties;
	
	@WebAction(Permission.NEED_AUTH)
	@RequestMapping("/")
	public String index() {
		if(StringUtils.isEmpty(WebContext.loggedUser())){
			return "redirect:/login";
		}
		return "redirect:/schedule";
	}
	
	@WebAction(Permission.ROOT_LOGIN)
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpSession sess) {
		if (!StringUtils.isEmpty(WebContext.loggedUser())) {
			return "redirect:/schedule";
		}
		return "login";
	}
	
	@WebAction(Permission.ROOT_LOGIN)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest req, String username, String password, RedirectAttributes ra) {
		HttpSession sess = req.getSession();
		if (!StringUtils.isEmpty(WebContext.loggedUser())) {
			return "redirect:/schedule";
		}
		if (username.equals(properties.getUsername()) && password.equals(properties.getPassword())) {
			sess.invalidate();
			req.getSession(true).setAttribute("user", username);
			return "redirect:/schedule";
		}
		ra.addFlashAttribute("error_msg", "用户名或密码错误");
		return "redirect:/login";
	}
	
	@WebAction(Permission.ROOT_LOGOUT)
	@RequestMapping(value = "/logout")
	public String logout(HttpSession sess) {
		sess.invalidate();
		return "redirect:/login";
	}
	
}
