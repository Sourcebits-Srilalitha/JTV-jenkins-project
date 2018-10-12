package com.vault.jtv.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.google.common.base.Predicate;
import com.vault.util.MessageConstants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.or;

@Configuration
@EnableSwagger2
@Profile(MessageConstants.S_DEV)
public class SwaggerConfig {

	
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName(MessageConstants.S_PUBLIC_API)
				.apiInfo(apiInfo()).select().paths(postPaths()).build();
	}

	private Predicate<String> postPaths() {
		return or(regex(MessageConstants.S_API), regex(MessageConstants.S_API));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(MessageConstants.S_TITLE)
				.description(MessageConstants.S_DESC)
				.build();
	}

}
