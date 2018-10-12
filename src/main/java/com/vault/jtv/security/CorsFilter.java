package com.vault.jtv.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;

import com.vault.util.MessageConstants;

@Singleton
public class CorsFilter implements Filter{
	
	@Value("${Allow.Origin}") 
	String origin;	

	@Value("${Allow.Methods}") 
	String methods;
	
	@Value("${Allow.Headers}") 
	String headers;

	@Value("${Max.Age}") 
	String age;
	String result = "*";
	
	
	public CorsFilter() {}
	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response,
    FilterChain filterChain) throws IOException, ServletException {

        if(response instanceof HttpServletResponse){
        HttpServletResponse alteredResponse = ((HttpServletResponse)response);
        String suppliedorigin = ((HttpServletRequest)request).getHeader(MessageConstants.ORIGIN);
        if(suppliedorigin != null){
        	Boolean isMatch = stringContainsItemFromList(suppliedorigin,Arrays.asList(origin.split(",")));
        }       
        addCorsHeader(alteredResponse,result.toString());
    }

    filterChain.doFilter(request, response);
    }
	
	public boolean stringContainsItemFromList(String inputStr, List<String> items) {
		result = items.parallelStream().filter(inputStr::contains).findAny().orElse("*");
	    return items.parallelStream().anyMatch(inputStr::contains);		
	}

    private void addCorsHeader(HttpServletResponse response,String newOrigin){
    	response.addHeader(MessageConstants.ALLOW_ORIGIN, newOrigin);
        response.addHeader(MessageConstants.ALLOW_METHODS, methods.toString());
        response.addHeader(MessageConstants.ALLOW_HEADERS, headers.toString());
        response.addHeader(MessageConstants.MAX_AGE, age.toString());
    }

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig filterConfig)throws ServletException{}
}
