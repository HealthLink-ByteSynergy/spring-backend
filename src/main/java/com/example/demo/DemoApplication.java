package com.example.demo;

import org.apache.poi.hssf.record.chart.ObjectLinkRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.CommandLineRunner;

import com.example.demo.entity.PatientEntity;
import com.example.demo.entity.Role;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.UserDuplicateEmailException;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UUIDService;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PatientRepository patientRepository;
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

	@Override
    public void run(String... args) throws UserDuplicateEmailException {
		try{
			UserEntity userEntity=new UserEntity();
			userEntity.setEmail("health-link@gmail.com");
			userEntity.setId(UUIDService.getUUID());
			userEntity.setUsername("bot");
			userEntity.setRole(Role.BOT);
			UserEntity user=userRepository.save(userEntity);

			PatientEntity patientEntity=new PatientEntity();
			patientEntity.setUserEntity(user);
			patientEntity.setPatientId(UUIDService.getUUID());
			patientRepository.save(patientEntity);
			
			System.out.println("User created successfully!");
		}
		catch(Exception ex){
			// throw new UserDuplicateEmailException(ex.getMessage());
			System.out.println(ex.getMessage());
		}
        
    }

}
