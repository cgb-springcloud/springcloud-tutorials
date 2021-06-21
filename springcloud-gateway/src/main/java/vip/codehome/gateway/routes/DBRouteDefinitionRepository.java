package vip.codehome.gateway.routes;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vip.codehome.gateway.service.RouteDefinitionService;

/**
 * @author dsys
 * @version v1.0 从数据库加载路由信息
 */
public class DBRouteDefinitionRepository implements RouteDefinitionRepository {
  private RouteDefinitionService service;
  private ApplicationEventPublisher publisher;

  public DBRouteDefinitionRepository(
      RouteDefinitionService service, ApplicationEventPublisher publisher) {
    this.service = service;
    this.publisher = publisher;
  }

  @Override
  public Flux<RouteDefinition> getRouteDefinitions() {
    return Flux.fromIterable(service.getDefinitions());
  }

  @Override
  public Mono<Void> save(Mono<RouteDefinition> route) {
    return route.flatMap(
        routeDefinition -> {
          service.addRoute(routeDefinition);
          return Mono.empty();
        });
  }

  @Override
  public Mono<Void> delete(Mono<String> routeId) {
    return routeId.flatMap(
        id -> {
          service.removeRoute(id);
          return Mono.empty();
        });
  }
}
