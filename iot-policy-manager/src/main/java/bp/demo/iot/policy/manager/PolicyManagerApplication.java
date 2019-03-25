package bp.demo.iot.policy.manager;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "bp.demo.iot.policy.manager",
        "bp.demo.iot.policy.kafka"})
public class PolicyManagerApplication {

  public static void main(String[] args) {
    SpringApplication.run(PolicyManagerApplication.class, args);
  }

  @Bean
  public ModelMapper modelMapper(){
    return  new ModelMapper();
  }

}
