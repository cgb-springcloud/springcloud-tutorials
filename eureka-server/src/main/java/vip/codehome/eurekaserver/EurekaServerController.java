package vip.codehome.eurekaserver;

import com.netflix.eureka.registry.InstanceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dsyslove@163.com
 * @createtime 2021/1/7--15:06
 * @description
 **/
@RestController
public class EurekaServerController {
    @Autowired
    InstanceRegistry instanceRegistry;
}
