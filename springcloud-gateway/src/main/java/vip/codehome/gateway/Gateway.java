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
/**
 * 1. 动态日志记录
 * 2. 鉴权
 * 3. 限流
 * 4. 动态路由
 * 5. websocket\文件上传\Mqtt
 * 6. 负载均衡、熔断
 * 7. 过滤器 GlobalFilter
 * 8. 统一异常处理
 */
public class Gateway {
  public static void main(String[] args) {
    SpringApplication.run(Gateway.class, args);
  }
}
