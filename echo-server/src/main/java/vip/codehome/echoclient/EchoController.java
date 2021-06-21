package vip.codehome.echoclient;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
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
public class EchoController {
    @GetMapping("/echo")
    public String echo(String msg) throws InterruptedException {
        System.out.println("echo wait...");
        Thread.sleep(1000*10);
        return "hello "+msg;
    }
}
