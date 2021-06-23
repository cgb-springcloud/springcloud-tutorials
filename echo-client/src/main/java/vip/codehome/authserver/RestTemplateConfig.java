package vip.codehome.authserver;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author dsys
 * @version v1.0
 **/
@Configuration
public class RestTemplateConfig {
  @LoadBalanced
  @Bean
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }
}
