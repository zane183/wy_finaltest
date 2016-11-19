package com.netease.javaweb.shop.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.netease.javaweb.shop.meta.Product;
import com.netease.javaweb.shop.meta.User;
import com.netease.javaweb.shop.service.AccountService;
import com.netease.javaweb.shop.service.ProductService;

@Controller
public class AccountController {
	@Resource
	private AccountService accountService;
	@Resource
	private ProductService productService;
	@RequestMapping("/account")
	public String account(HttpServletRequest req,ModelMap map){
		HttpSession session=req.getSession();
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "login";
		}
		map.put("buyList", accountService.getBuyList(user));
		return "account";
	}
	
	@RequestMapping("/public")
	public String toPublic(ModelMap map,HttpServletRequest req){
		HttpSession session=req.getSession();
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "login";
		}
		map.put("user", user);
		return "public";
	}
	
	@RequestMapping("/publicSubmit")
	public String addProduct(@ModelAttribute("product") Product product,ModelMap map,HttpServletRequest req){
		HttpSession session=req.getSession();
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "login";
		}
		if(productService.insert(product)>0){
			map.put("user", user);
			map.put("product", product);
			return "publicSubmit";
		}else{
			map.put("user", user);
			return "public";
		}
		
	}
	
	@RequestMapping("/edit")
	public String toEdit(@RequestParam int id,HttpServletRequest req,ModelMap map){
		HttpSession session=req.getSession();
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "login";
		}
		Product product=productService.getProductById(id);
		map.put("user", user);
		map.put("product", product);
		return "edit";
	}
	
	@RequestMapping("/editSubmit")
	public String editSubmit(@ModelAttribute Product product,ModelMap map,HttpServletRequest req){
		HttpSession session=req.getSession();
		User user=(User) session.getAttribute("user");
		if(user==null){
			return "login";
		}
		productService.update(product);
		map.put("product", product);
		map.put("user", user);
		return "editSubmit";
	}
}
