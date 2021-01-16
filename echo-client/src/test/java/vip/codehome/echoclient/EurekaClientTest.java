package vip.codehome.echoclient;

import com.netflix.discovery.EurekaClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dsyslove@163.com
 * @createtime 2021/1/9--14:32
 * @description
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class EurekaClientTest {
    @Autowired
    EurekaClient eurekaClient;
    @Test
    public void test(){
        eurekaClient.getApplications();


    }
}
