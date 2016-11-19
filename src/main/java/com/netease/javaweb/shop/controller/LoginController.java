package com.netease.javaweb.shop.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.javaweb.shop.meta.Page;
import com.netease.javaweb.shop.meta.Product;
import com.netease.javaweb.shop.meta.User;
import com.netease.javaweb.shop.service.ProductService;
import com.netease.javaweb.shop.service.UserService;

@Controller
public class LoginController {
	@Resource
	private UserService userService;
	
	@Resource
	private ProductService productService;
	
	@RequestMapping(value="/login")
	public String toLogin(){
		System.out.println(222);
		return "login";
	}
	
	@RequestMapping(value="/api/login")
	public String loginCheck(@ModelAttribute User user,ModelMap map,HttpServletRequest req,HttpServletResponse rep){
		
		//cookie的判断
		Cookie[] cookies=req.getCookies();
		if(cookies!=null && cookies.length>1){
			User cookieUser=new User();
			
			for(Cookie cookie:cookies){								
				if(cookie.getName().equals("user")){
					cookieUser.setUserName(cookie.getValue());
				}else if(cookie.getName().equals("password")){
					cookieUser.setPassword(cookie.getValue());
				}								
			}
			if(user.getUserName().equals(cookieUser.getUserName()) &&
					user.getPassword().equals(user.getPassword())){	
				cookieUser=userService.getUser(cookieUser);
				if(cookieUser.getNickName()!=null){
					req.getSession().setAttribute("user", cookieUser);
					map.put("code", 200);
					rep.setStatus(200);
					return "";	
				}						
			}
		}
		
		//Session的判断
		HttpSession session=req.getSession();
		if(session.getAttribute("user")!=null){
			map.put("code", 200);
			rep.setStatus(200);
			return "";
		}
		User detailUser=userService.getUser(user);
		if(detailUser!=null){
			req.getSession().setAttribute("user", detailUser);
			Cookie username=new Cookie("user", user.getUserName());
			Cookie password=new Cookie("password",user.getPassword());
			rep.addCookie(username);
			rep.addCookie(password);
			map.put("code", 200);
			return "";
		}else{
			rep.setStatus(300);
			map.put("code", 300);
			map.put("message", "用户名和密码不对");
			return "";
		}
		
	}
	
	@RequestMapping("/")
	public String login(HttpServletRequest req,ModelMap map,@RequestParam(required=false,defaultValue="0",name="type") int type
			,@ModelAttribute Page page){
		if(page.getPagenumber()==0){
			page=new Page(0,8);
			map.put("page", page);
		}
		System.out.println(page);
		HttpSession session=req.getSession();
		User user=(User) session.getAttribute("user");
		if(user!=null){
			List<Product> productList=productService.queryProduct(user,page);
			map.put("productList", productList);
			map.put("user", user);
			req.setAttribute("type", type);
			return "index";
		}else{
			List<Product> productList=productService.query();
			map.put("productList", productList);
			return "index";			
		}
		
		
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest req,HttpServletResponse rep){		
		HttpSession session=req.getSession();
		if(session.getAttribute("user")!=null){
			session.invalidate();
		}
		return "login";
	}
	

}
