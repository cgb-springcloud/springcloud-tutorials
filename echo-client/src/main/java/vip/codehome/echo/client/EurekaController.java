package vip.codehome.echo.client;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;

import vip.codehome.common.AuthHelper;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author dsyslove@163.com
 * @createtime 2021/1/6--23:10
 * @description
 **/
@RestController
public class EurekaController {
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    EurekaDiscoveryClient eurekaDiscoveryClient;
    @Autowired
    EurekaAutoServiceRegistration eurekaAutoServiceRegistration;
    @Autowired
    EurekaClient eurekaClient;
    @GetMapping("/down")
    public String down(){
        eurekaAutoServiceRegistration.stop();
        return "down";
    }
    @GetMapping("/up")
    public String up(){
    	System.out.println(AuthHelper.getToken());
        eurekaAutoServiceRegistration.start();
        return AuthHelper.getToken();
    }
    @GetMapping("/op")
    public Applications op(String op){
        return eurekaClient.getApplications();
    }
    @GetMapping("/services")
    public void listServices(String serviceId){
        List<ServiceInstance> serviceInstances=discoveryClient.getInstances(serviceId);
        serviceInstances= eurekaDiscoveryClient.getInstances(serviceId);
    }
    @GetMapping("/loadbalancer")
    public void loadbalancer(){
        ServiceInstance serviceInstance=loadBalancerClient.choose("test");
        String url=String.format("http://%s:%s/server/get",serviceInstance.getHost(),serviceInstance.getPort());
    }
//    @Autowired
//    RestTemplate restTemplate;
//    @GetMapping("/gracefuldown")
//    public String testDown(){
//     return    restTemplate.getForObject("http://echo-server/echo?msg=client",String.class);
//    }
}
