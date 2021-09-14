package vip.codehome.authserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vip.codehome.common.AuthHelper;

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
        return "获取到的cookie: "+AuthHelper.getToken();
    }
}
