package vip.codehome.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * curl -X POST http://127.0.0.1:8080/actuator/pause
 */
@SpringBootApplication
@EnableEurekaClient
public class EchoClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EchoClientApplication.class, args);
    }

}
