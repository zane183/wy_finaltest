package com.netease.javaweb.shop.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.netease.javaweb.shop.meta.Product;
import com.netease.javaweb.shop.meta.User;
import com.netease.javaweb.shop.service.ProductService;

@Controller
public class ProductController {
	
	@Resource
	private ProductService productService;
	
	@RequestMapping("/show")
	public String show(@RequestParam("id") int id,HttpServletRequest req,ModelMap map){
		User user=(User) req.getSession().getAttribute("user");
		if(user==null)
			return "login";
		Product product=productService.getProductById(id);
		map.put("user", user);
		map.put("product", product);
		return "show";
	}
	
	@RequestMapping("/api/buy")
	public String buy(@RequestParam int id,HttpServletRequest req,
			HttpServletResponse rep,ModelMap map){
		User user=(User) req.getSession().getAttribute("user");
		if(user==null)
			return "login";
		try{
		productService.insert(id, user);
		}catch(Exception e){
			rep.setStatus(304);
			map.put("code", 304);
			map.put("message", "购买失败");
			map.put("result", false);
			return "";
		}
		rep.setStatus(200);
		map.put("code", 200);
		map.put("result", true);
		return "";
	}
	
	@RequestMapping("/api/delete")
	public String delete(@RequestParam int id,HttpServletRequest req ,ModelMap map
			,HttpServletResponse rep){
		User user=(User) req.getSession().getAttribute("user");
		if(user==null)
			return "login";
		boolean result;
		try{
			result=productService.delete(id);
		}catch(Exception e){			
			rep.setStatus(300);
			map.put("code", 300);
			map.put("message", "删除失败");
			map.put("result", false);
			return "";
	
		}
		if(result){
			rep.setStatus(200);
			map.put("code", 200);
			return "";
		}else{
			rep.setStatus(300);
			map.put("code", 300);
			map.put("message", "删除失败");
			map.put("result", false);
			return "";
		}
	}
	
	
	
	
}
