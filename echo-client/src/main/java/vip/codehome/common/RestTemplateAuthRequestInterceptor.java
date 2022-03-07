package vip.codehome.common;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@Component
public class RestTemplateAuthRequestInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest req = ((ServletRequestAttributes) requestAttributes).getRequest();
		Enumeration<String> headerNames=req.getHeaderNames();
		if(headerNames!=null) {
			while(headerNames.hasMoreElements()) {
				String name=headerNames.nextElement();
				String values=req.getHeader(name);
				System.out.println(name+","+values);
				request.getHeaders().add(name, values);
			}
		}
		return execution.execute(request, body);
	}

}
