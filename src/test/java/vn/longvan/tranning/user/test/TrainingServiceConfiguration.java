package vn.longvan.tranning.user.test;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.longvan.tranning.spring.user.service.UserService;

@Configuration
public class TrainingServiceConfiguration {

    @Bean
   UserService getUserService(){
       return new UserService();
   }
}
