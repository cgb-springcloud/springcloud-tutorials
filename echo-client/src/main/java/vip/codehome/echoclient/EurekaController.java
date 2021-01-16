package vip.codehome.echoclient;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dsyslove@163.com
 * @createtime 2021/1/6--23:10
 * @description
 **/
@RestController
public class EurekaController {
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
        eurekaAutoServiceRegistration.start();
        return "up";
    }
    @GetMapping("/op")
    public Applications op(String op){
        return eurekaClient.getApplications();
    }
}
