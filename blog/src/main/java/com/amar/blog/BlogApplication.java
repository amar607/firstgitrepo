package com.amar.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@OpenAPIDefinition(
		info =@Info(
				title = "Spring boot Blog app Rest APIs",
		description = "Spring boot Blog app Rest APIs documentation",
		version = "v1.0",
		contact = @Contact(
				name = "Amar",
				email = "amarnathkeshri607@gmail.com",
				url="url yet to update"
		),
		license = @License(
				name = "Apache 2.0",
				url = "license url yet to be updated"
		)
	),
		externalDocs = @ExternalDocumentation(
				description = "Spring boot Blog App documentation"
		)

)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.amar.blog"})
@SpringBootApplication()
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
