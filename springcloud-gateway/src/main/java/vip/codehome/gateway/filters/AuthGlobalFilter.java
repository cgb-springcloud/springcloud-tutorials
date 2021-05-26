package vip.codehome.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zyw
 * @mail dsyslove@163.com
 * @createtime 2021/5/25--11:15
 * @description
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    // 从查询参数拿token
    String token = exchange.getRequest().getQueryParams().getFirst("tk");
    if (token == null || token.isEmpty()) {
      // 从cookie拿token
      token = exchange.getRequest().getCookies().getFirst("tk").getValue();
      if (token == null || token.isEmpty()) {
        // 从请求头拿token
        token = exchange.getRequest().getHeaders().getFirst("tk");
      }
    }

    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return -100;
  }
}
