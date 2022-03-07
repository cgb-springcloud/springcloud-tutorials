package vip.codehome.echo.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@GetMapping("/logGet")
	public Map<String,Object> logGet(@RequestParam Map<String,Object> params) {
		return params;
	}
	@PostMapping("/logPost")
	public UserDO logGet(@RequestBody UserDO userDO) {
		return userDO;
	}
	@PostMapping("/logPostParam")
	public  Map<String,Object> logPostParam(@RequestParam Map<String,Object> params) {
		return params;
	}
	private static class UserDO{
		String userName;
		String passWord;
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPassWord() {
			return passWord;
		}
		public void setPassWord(String passWord) {
			this.passWord = passWord;
		}
		
	}
}
