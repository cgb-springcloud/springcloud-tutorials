package vip.codehome.common;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.RequestContextFilter;

@Configuration
public class RestTemplateConfiguration {
	@Autowired
	RestTemplateAuthRequestInterceptor interceptor;
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate= new RestTemplate();
		restTemplate.setInterceptors(Collections.singletonList(interceptor));
		return restTemplate;
	}
//	@Bean
//	public RequestContextFilter requestContextFilter(){
//	    RequestContextFilter requestContextFilter = new RequestContextFilter();
//	    requestContextFilter.setThreadContextInheritable(true);
//	    return requestContextFilter;
//	}
}
