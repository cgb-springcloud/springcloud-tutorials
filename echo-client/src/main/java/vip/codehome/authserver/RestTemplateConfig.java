package vip.codehome.authserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author dsys
 * @version v1.0
 **/
@Configuration
@EnableConfigurationProperties(RemoteServiceProperties.class)
public class RestTemplateConfig {
  @Autowired
  RemoteServiceProperties properties;
  @LoadBalanced
  @Bean(name = "LoadBalancedRestTemplate")
  public RestTemplate loadBalancedRestTemplate(){
    SimpleClientHttpRequestFactory requestFactory=new SimpleClientHttpRequestFactory();
    requestFactory.setConnectTimeout(properties.getTimeout());
    requestFactory.setReadTimeout(properties.getTimeout());
    RestTemplate restTemplate=new RestTemplate(requestFactory);
    return restTemplate;
  }
  @Bean(name = "CommonRestTemplate")
  public RestTemplate commonRestTemplate(){
    SimpleClientHttpRequestFactory requestFactory=new SimpleClientHttpRequestFactory();
    requestFactory.setConnectTimeout(properties.getTimeout());
    requestFactory.setReadTimeout(properties.getTimeout());
    RestTemplate restTemplate=new RestTemplate(requestFactory);
    return restTemplate;
  }
}
