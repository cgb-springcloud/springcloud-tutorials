package vip.codehome.echo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
public class EchoClientController {
	@Autowired
	RestTemplate restTemplate;
	@GetMapping("/cookie")
	public String testCookie() {
		return remote();
	}
	@Async
	public String remote() {
		return restTemplate.getForObject("http://ECHO-SERVER/echo?msg=test", String.class);
	}
}
