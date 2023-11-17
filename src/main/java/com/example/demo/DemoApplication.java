package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.CommandLineRunner;

import com.example.demo.repository.UserRepository;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private UserRepository userRepository;
	//need to create a bot user on running
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:5173", "http://localhost:5174")
				.allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
				.allowCredentials(true);
			}
		};
	}

	// @Override
    // public void run(String...args ) throws Exception {
    //     // Create a user and save it to the database
    //     User user = new User();
    //     user.setUsername("exampleUser");
    //     user.setPassword("password123");

    //     userRepository.save(user);

    //     System.out.println("User created successfully!");
    // }

}
