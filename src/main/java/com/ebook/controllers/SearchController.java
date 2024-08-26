package com.ebook.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
public class SearchController {

	
	@GetMapping("/")
	public List<String> getsuther(@RequestParam String q) {
		
		
		return null;
	}
	
	
	
}
