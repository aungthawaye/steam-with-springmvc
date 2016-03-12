package com.gnomesys.web;

import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SteamAuthController {

	private final static Logger log = Logger.getLogger(SteamAuthController.class);

	private SteamOpenID steamOpenID = new SteamOpenID();

	@RequestMapping(value = "/home_page", method = { RequestMethod.GET })
	public ModelAndView homePage(HttpServletRequest request) {
		return new ModelAndView("home");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/post_login_page", method = { RequestMethod.GET })
	public ModelAndView postLoginPage(HttpServletRequest request) {
		Map params = request.getParameterMap();
		Enumeration enumeration = request.getParameterNames();
		
		while(enumeration.hasMoreElements()){
			String key = (String) enumeration.nextElement();
			log.debug(key + " \t " + request.getParameter(key));
		}
		return new ModelAndView("post_login");
	}

	@RequestMapping(value = "/login_page", method = { RequestMethod.GET })
	public ModelAndView loginPage(HttpServletRequest request) {

		log.debug("Trying to call Steam OpenID...");

		String openIdRedirectUrl = steamOpenID.login("http://localhost:8080/steam-with-springmvc/post_login_page.do");

		log.debug("Redirect URL : " + openIdRedirectUrl);

		return new ModelAndView("redirect:" + openIdRedirectUrl);
	}
}
