package com.netease.javaweb.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PageController {
	
	@RequestMapping("/nextPage")
	public String nextPage(){
		return "index";
	}
	
	
}
