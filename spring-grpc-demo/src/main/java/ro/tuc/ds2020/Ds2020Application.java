package ro.tuc.ds2020;

import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import lombok.extern.slf4j.Slf4j;
import ro.tuc.ds2020.entities.MyUser;
import ro.tuc.ds2020.repositories.MyUserRepository;
import ro.tuc.ds2020.security.JWTTokenHelper;
import ro.tuc.ds2020.services.UserService;

import java.util.TimeZone;

@Slf4j
@SpringBootApplication
@Validated
public class Ds2020Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Ds2020Application.class);
    }


    public static void main(String[] args) {
        // BasicConfigurator.configure();
        // ApplicationContext  ctx = new ClassPathXmlApplicationContext();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(Ds2020Application.class, args);
        
    
    }       
    @Bean 
    public CommandLineRunner hihi(UserService userService){
        return args ->{
            log.info("[main] : find user admin ");
            String regex = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvbG9naW4iLCJleHAiOjE2NzMzODY2MzF9.q2kbZVd_NmOuqTxoKHetyeGxMsdTdHGEVgASoQS4lx8";
            JWTTokenHelper helper = new JWTTokenHelper();
            helper.getUsernameFromToken(regex);
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
