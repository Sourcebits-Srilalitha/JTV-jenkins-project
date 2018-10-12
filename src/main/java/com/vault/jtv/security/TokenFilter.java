package com.vault.jtv.security;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.vault.jtv.Application;
import com.vault.jtv.model.UserAccount;
import com.vault.jtv.services.TokenService;
import com.vault.util.MessageConstants;

import io.jsonwebtoken.JwtException;


public class TokenFilter implements Filter
{
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
    
   @Autowired
   TokenService keyCloackTokenService;
 
    @Value("${jwt.auth.header}")
    String authHeader;
    public TokenFilter() {
		// TODO Auto-generated constructor stub
	}
 
    @Override public void init(FilterConfig filterConfig) throws ServletException  {}
    @Override public void destroy() {}
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
         	
    	authHeader = MessageConstants.X_AUTH_TOKEN;
    	final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        final String authHeaderVal = httpRequest.getHeader(authHeader);
       
        String requestMethod = httpRequest.getMethod();
        
        if(requestMethod.equals(MessageConstants.OPTIONS)){//bypass authentication for options calls
        	chain.doFilter(httpRequest, httpResponse);
        }else{
        
	        if (null==authHeaderVal)
	        {
	            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return;
	        }
	 
	        try
	        {
	        	if(keyCloackTokenService != null){
		        	UserAccount dbUser = keyCloackTokenService.getUser(authHeaderVal);
		            httpRequest.setAttribute(MessageConstants.JWT_USER, dbUser);
	        	}
	        }
	        catch(JwtException e)
	        {
	        	e.printStackTrace();
	        	logger.error(MessageConstants.NO_AUTHENTICATION+e.getMessage());
	            httpResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
	            return;
	        }
	 
	        chain.doFilter(httpRequest, httpResponse);
        }        
        
    }
    

}
