package vip.codehome.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import vip.codehome.common.Constants;

// @Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
  public static Logger log = LoggerFactory.getLogger(AuthGlobalFilter.class);

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    // 从查询参数拿token
    try {
      String token = exchange.getRequest().getQueryParams().getFirst(Constants.TOKEN_KEY);
      if (token == null || token.isEmpty()) {
        // 从cookie拿token
        token = exchange.getRequest().getCookies().getFirst(Constants.TOKEN_KEY).getValue();
        if (token == null || token.isEmpty()) {
          // 从请求头拿token
          token = exchange.getRequest().getHeaders().getFirst(Constants.TOKEN_KEY);
        }
      }
      log.debug("获取到token:{}", token);

    } catch (Exception e) {
      log.error(e.getMessage());
    }
    log.info("开始写cookie到客户端");
    //	    exchange
    //	    .getResponse()
    //	    .getCookies()
    //	    .set(
    //	    		Constants.TOKEN_KEY,
    //	        ResponseCookie.from(
    //	        		Constants.TOKEN_KEY,
    //	                "haha")
    //	            .domain("127.0.0.1")
    //	          //  .domain("192.28.4.4")
    //	            .path("/")
    //	            .maxAge(-1)
    //	            .build());
    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return -100;
  }
}
