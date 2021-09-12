package vip.codehome.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/***
 * 需要通过spring.cloud.routes.filters 配置在具体路由下，只作用在当前路由上或通过spring.cloud.default-filters配置在全局，作用在所有路由上
 */
//@Component
public class RequestTimeFilter implements GlobalFilter, Ordered {
  private static final String START_TIME_TAG = "start_time_tag";

  @Override
  public int getOrder() {
    return 0;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    exchange.getAttributes().put(START_TIME_TAG, System.currentTimeMillis());

    return chain
        .filter(exchange)
        .then(
            Mono.fromRunnable(
                () -> {
                  Long startTime = exchange.getAttribute(START_TIME_TAG);
                  System.out.println(
                      exchange.getRequest().getURI().getRawPath()
                          + ",waste Time:"
                          + (System.currentTimeMillis() - startTime)
                          + "ms");
                }));
  }
}
