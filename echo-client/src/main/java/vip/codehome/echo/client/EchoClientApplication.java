package vip.codehome.echo.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * curl -X POST http://127.0.0.1:8080/actuator/pause
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan("vip.codehome")
@EnableAsync
public class EchoClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EchoClientApplication.class, args);
    }

}
