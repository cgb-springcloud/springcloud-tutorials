package vip.codehome.gateway.domain;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

/**
 * @author zyw
 * @mail dsyslove@163.com
 * @createtime 2021/5/25--15:42
 * @description
 */
public class AccessRecord {
  String path;
  String schema;
  String method;
  String targetUri;
  String remoteAddress;
  HttpHeaders headers;
  String body;
  MultiValueMap<String, String> formData;

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getSchema() {
    return schema;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getTargetUri() {
    return targetUri;
  }

  public void setTargetUri(String targetUri) {
    this.targetUri = targetUri;
  }

  public String getRemoteAddress() {
    return remoteAddress;
  }

  public void setRemoteAddress(String remoteAddress) {
    this.remoteAddress = remoteAddress;
  }

  public HttpHeaders getHeaders() {
    return headers;
  }

  public void setHeaders(HttpHeaders headers) {
    this.headers = headers;
  }

  public MultiValueMap<String, String> getFormData() {
    return formData;
  }

  public void setFormData(MultiValueMap<String, String> formData) {
    this.formData = formData;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
