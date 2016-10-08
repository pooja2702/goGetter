package org.sample.poc.bookstore.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig extends WebMvcConfigurerAdapter {

	@Bean
	public Docket bookStoreapi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("bookstore-api")
				.select()
				.apis(RequestHandlerSelectors.any()).paths(regex("/books.*"))
				.build().apiInfo(apiInfo());
	}


	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("BookStore API")
				.description("A RESTful API consisting of services to fetch books and orders in a bookstore.")
				.termsOfServiceUrl("")
				.version("1.0")
				.build();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.
		addResourceHandler("/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
		registry.
		addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}