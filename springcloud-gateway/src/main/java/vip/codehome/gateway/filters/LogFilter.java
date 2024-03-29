package vip.codehome.gateway.filters;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vip.codehome.gateway.domain.AccessRecord;

@Component
public class LogFilter implements GlobalFilter, Ordered {

  private static final String START_TIME = "startTime";
  private static final List<HttpMessageReader<?>> messageReaders =
      HandlerStrategies.withDefaults().messageReaders();
  private Logger log = LoggerFactory.getLogger(LogFilter.class);

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
	try {
	    ServerHttpRequest request = exchange.getRequest();
	    // 请求路径
	    String path = request.getPath().pathWithinApplication().value();
	    // 请求schema: http/https
	    String scheme = request.getURI().getScheme();
	    // 请求方法
	    HttpMethod method = request.getMethod();
	    // 路由服务地址
	    URI targetUri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
	    // 请求头
	    HttpHeaders headers = request.getHeaders();
	    // 设置startTime
	    exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
	    // 获取请求地址
	    InetSocketAddress remoteAddress = request.getRemoteAddress();

	    MultiValueMap<String, String> formData = null;

	    AccessRecord accessRecord = new AccessRecord();
	    accessRecord.setPath(path);
	    accessRecord.setSchema(scheme);
	    accessRecord.setMethod(method.name());
	    accessRecord.setTargetUri(targetUri.toString());
	    accessRecord.setRemoteAddress(remoteAddress.toString());
	    accessRecord.setHeaders(headers);

	    if (method == HttpMethod.GET) {
	      formData = request.getQueryParams();
	      accessRecord.setFormData(formData);
	      writeAccessRecord(accessRecord);
	    }

	    if (method == HttpMethod.POST) {
	      Mono<Void> voidMono = null;
	      //这里报错，前端没有传content-Type
	      if (headers.getContentType().equals(MediaType.APPLICATION_JSON)) {
	        // JSON
	        voidMono = readBody(exchange, chain, accessRecord);
	      }

	      if (headers.getContentType().equals(MediaType.APPLICATION_FORM_URLENCODED)) {
	        // x-www-form-urlencoded
	        // voidMono = readFormData(exchange, chain, accessRecord);
	        voidMono = readBody(exchange, chain, accessRecord);
	      }

	      if (voidMono != null) {
	        return voidMono;
	      }
	    }
	}catch(Exception e) {
		e.printStackTrace();
	}

    return chain.filter(exchange);
  }

  private Mono<Void> readFormData(
      ServerWebExchange exchange, GatewayFilterChain chain, AccessRecord accessRecord) {
    return null;
  }

  private Mono<Void> readBody(
      ServerWebExchange exchange, GatewayFilterChain chain, AccessRecord accessRecord) {

    return DataBufferUtils.join(exchange.getRequest().getBody())
        .flatMap(
            dataBuffer -> {
            	try {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    Flux<DataBuffer> cachedFlux =
                        Flux.defer(
                            () -> {
                              DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                              DataBufferUtils.retain(buffer);
                              return Mono.just(buffer);
                            });

                    // 重写请求体,因为请求体数据只能被消费一次
                    ServerHttpRequest mutatedRequest =
                        new ServerHttpRequestDecorator(exchange.getRequest()) {
                          @Override
                          public Flux<DataBuffer> getBody() {
                            return cachedFlux;
                          }
                        };

                    ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

                    return ServerRequest.create(mutatedExchange, messageReaders)
                        .bodyToMono(String.class)
                        .doOnNext(
                            objectValue -> {
                              accessRecord.setBody(objectValue);
                              writeAccessRecord(accessRecord);
                            })
                        .then(chain.filter(mutatedExchange));
            	}catch(Exception e) {
            		e.printStackTrace();
            		return null;
            	}

            });
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE;
  }

  /**
   * TODO 异步日志
   *
   * @param accessRecord
   */
  private void writeAccessRecord(AccessRecord accessRecord) {

    log.info(
        "\n\n start------------------------------------------------- \n "
            + "请求路径:{}\n "
            + "scheme:{}\n "
            + "请求方法:{}\n "
            + "目标服务:{}\n "
            + "请求头:{}\n "
            + "远程IP地址:{}\n "
            + "表单参数:{}\n "
            + "请求体:{}\n "
            + "end------------------------------------------------- \n ",
        accessRecord.getPath(),
        accessRecord.getSchema(),
        accessRecord.getMethod(),
        accessRecord.getTargetUri(),
        accessRecord.getHeaders(),
        accessRecord.getRemoteAddress(),
        accessRecord.getFormData(),
        accessRecord.getBody());
  }
}
