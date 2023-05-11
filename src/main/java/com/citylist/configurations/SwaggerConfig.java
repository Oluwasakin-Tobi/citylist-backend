package com.citylist.configurations;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() { 
	    return new Docket(DocumentationType.SWAGGER_2)
	          .select()
	          .apis(Predicates.or(REQUEST_HANDLERS))
	          .paths(PathSelectors.any())
	          .build()
	          .apiInfo(apiInfo())
	          .securitySchemes(Collections.singletonList(apiKey()))
	          .securityContexts(Collections.singletonList(securityContext()))
	          .enable(true);      
	}
	
	@Bean
	UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder()
				.displayRequestDuration(true)
				.validatorUrl("")
				.build();
	}

	private ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	          .title("CityList")
	          .description("This is a documentation of the backend APIs for citylist app")
	          .version("1.0")
	          .build();
	}
	
	private ApiKey apiKey() {
		return new ApiKey("Bearer", "Authorization", "header");
	}
	
	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("/.*"))
				.build();
	}
	
	private List<SecurityReference> defaultAuth(){
		return Collections.singletonList(new SecurityReference("Bearer", new AuthorizationScope[0]));
	}
	
	private static final Set<Predicate<RequestHandler>> REQUEST_HANDLERS = Stream.of(
			"com.citylist.controller").map(RequestHandlerSelectors::basePackage).collect(Collectors.toSet());

}
