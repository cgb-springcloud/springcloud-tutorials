package vip.codehome.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan("vip.codehome")
@EnableAspectJAutoProxy(proxyTargetClass = true)
/** https://blog.csdn.net/forezp/article/details/85057268 */
public class Gateway {
  public static void main(String[] args) {
    SpringApplication.run(Gateway.class, args);
  }
}
