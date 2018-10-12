package com.vault.jtv;

import javax.servlet.Filter;
import javax.sql.DataSource;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.vault.jtv.repository.ArticleTypeRep;
import com.vault.jtv.security.CorsFilter;
import com.vault.jtv.security.JwtFilter;
import com.vault.util.MessageConstants;


@PropertySources({
	@PropertySource("file:${CONF_DIR}/security-${spring.profiles.active}.properties"),
	@PropertySource("file:${CONF_DIR}/deployment-${spring.profiles.active}.properties")
})
@Configuration
@ComponentScan(MessageConstants.COMPONENT_SCAN)
@SpringBootApplication(scanBasePackages={MessageConstants.COMPONENT_SCAN})
public class Application extends SpringBootServletInitializer  {    
	
	@Value("${spring.datasource.username}") 
	String username;
	
	@Value("${spring.datasource.password}") 
	String password;
	
	@Value("${spring.datasource.url}") 
	String url;
	
	@Autowired
    DataSource dataSource;
	
    @Autowired
    ArticleTypeRep articletyperep;
    
 
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    
	public static void main(String[] args) throws Exception{
		SpringApplication.run(new Object[]{Application.class}, args);		
	}
	
	
	@Bean
	@Profile(MessageConstants.H2)
	public ServletRegistrationBean h2servletRegistration() {
	    ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
	    registration.addUrlMappings(MessageConstants.CONSOLE);
	    return registration;
	}
	
	
	@Bean
	 public FilterRegistrationBean corsFilterRegistration() {

	     FilterRegistrationBean registration = new FilterRegistrationBean();
	     registration.setFilter(corsFilter());
	     registration.addUrlPatterns(MessageConstants.API);	     
	     registration.setName(MessageConstants.CORSFILTER);
	     registration.setOrder(1);
	     return registration;
	 } 

	 @Bean(name = MessageConstants.CORSFILTER)
	 public Filter corsFilter() {
	     return new CorsFilter();
	 }
	
	 @Bean
	 public FilterRegistrationBean vvaultFilterRegistration() {

	     FilterRegistrationBean registration = new FilterRegistrationBean();
	     registration.setFilter(vaultFilter());
	     registration.addUrlPatterns(MessageConstants.API_VAULT);	     
	     registration.setName(MessageConstants.VAULT_FILTER);
	     registration.setOrder(1);
	     return registration;
	 } 

	 @Bean(name = MessageConstants.VAULT_FILTER)
	 public Filter vaultFilter() {
	     return new JwtFilter();
	 }
	 
	
}