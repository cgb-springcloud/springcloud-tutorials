package vip.codehome.gateway.routes;

import cn.hutool.json.JSONUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 基于apollo的动态路由
 * @version 1.0
 * @author: dsys
 * @date 2022/3/7 14:57
 * @description TODO
 **/
@Slf4j
@Component
@Configuration
public class ApolloRoteDefinitionRepository implements RouteDefinitionRepository, ApplicationEventPublisherAware {

    @ApolloConfig
    private Config config;

    private ApplicationEventPublisher publisher;

    private final RefreshScope refreshScope;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;

    }

    public ApolloRoteDefinitionRepository(RefreshScope refreshScope) {
        this.refreshScope = refreshScope;
    }

    /**
     * 路由配置Key
     */
    private static final String SERVER_ROUTER = "serverRouter";

    /**
     * 应用namespace
     */
    private static final String APP_SERVER = "application";

    /**
     * 监听namespace(application)
     *
     * @param configChangeEvent ConfigChangeEvent
     */
    @ApolloConfigChangeListener(APP_SERVER)
    private void onchange(ConfigChangeEvent configChangeEvent) {
        if (configChangeEvent.isChanged(SERVER_ROUTER)) {
            log.info("<-----------------------------[网关-监听到路由配置变化,开始刷新路由信息]----------------------------->");
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            log.info("<-----------------------------[网关-监听到路由配置变化,路由信息刷新完毕]----------------------------->");
        }
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        String property = config.getProperty(SERVER_ROUTER, null);
        if (StringUtils.isEmpty(property)) {
            log.error("网关路由配置为空,请检查Apollo配置信息,Key:[{}],是否为空", SERVER_ROUTER);
            throw new RuntimeException("网关路由配置为空,请检查Apollo配置信息,Key:serverRouterList,是否为空");
        }
        log.info("<-----------------------------[网关-动态路由刷新执行]----------------------------->");
        List<RouteDefinition> routeDefinitionList =  JSONUtil.toList(property, RouteDefinition.class);
        return Flux.fromIterable(routeDefinitionList);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }
}