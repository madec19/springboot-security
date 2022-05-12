package com.man.springboot.security.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilterChainController {

	/*
	@Autowired
	private FilterChainProxy filterChainProxy;

	// @Override
	@RequestMapping("/filterChain")
	public @ResponseBody Map<Integer, Map<Integer, String>> getSecurityFilterChainProxy1() {
		return this.getSecurityFilterChainProxy2();
	}

	public Map<Integer, Map<Integer, String>> getSecurityFilterChainProxy2() {
		Map<Integer, Map<Integer, String>> filterChains = new HashMap<Integer, Map<Integer, String>>();
		int i = 1;
		for (SecurityFilterChain secfc : this.filterChainProxy.getFilterChains()) {
			// filters.put(i++, secfc.getClass().getName());
			Map<Integer, String> filters = new HashMap<Integer, String>();
			int j = 1;
			for (Filter filter : secfc.getFilters()) {
				filters.put(j++, filter.getClass().getName());
			}
			filterChains.put(i++, filters);
		}
		return filterChains;
	}
	*/
}
