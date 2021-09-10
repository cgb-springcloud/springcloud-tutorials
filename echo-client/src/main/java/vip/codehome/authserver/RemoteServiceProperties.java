package vip.codehome.authserver;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dsys
 * @version v1.0
 **/
@ConfigurationProperties(prefix = "les.remote")
public class RemoteServiceProperties {
  Integer timeout=1000*10;

  public Integer getTimeout() {
    return timeout;
  }

  public void setTimeout(Integer timeout) {
    this.timeout = timeout;
  }
}
