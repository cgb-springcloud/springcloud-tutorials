package vip.codehome.authserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author dsys
 * @version v1.0
 **/
@Component
public class RemoteServiceTemplate {
  private static Logger logger = LoggerFactory.getLogger(RemoteServiceTemplate.class);
  private static final ObjectMapper mapper = new ObjectMapper();
  @Qualifier("LoadBalancedRestTemplate")
  RestTemplate lbRestTemplate;
  public String doGet(String url){
     return lbRestTemplate.getForObject(url,String.class);
  }
  public String doGet(String url,Map<String,String> params){
    HttpHeaders headers=new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
    UriComponentsBuilder builder=UriComponentsBuilder.fromHttpUrl(url);
    if(params!=null&&params.size()>0){
      for(Map.Entry<String,String> entry:params.entrySet()){
        builder.queryParam(entry.getKey(),entry.getValue());
      }
    }
    HttpEntity<?> httpEntity=new HttpEntity<>(headers);
    return lbRestTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,httpEntity,String.class).getBody();
  }
  public <T> T doGet(String url,Map<String,String> params, TypeReference<T> valueTypeRef){
    String result=doGet(url,params);
    try {
      return mapper.readValue(result,valueTypeRef);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  public String doPostFormParam(String url,Map<String,String> params){
    return lbRestTemplate.postForEntity(url,getMultiValueMap(null,null,params),String.class).getBody();
  }
  public <T> T doPostFormParam(String url,Map<String,String> params,TypeReference<T> valueTypeRef){
    String result=doPostFormParam(url,params);
    try {
      return mapper.readValue(result,valueTypeRef);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }
  }
  public String doPostJson(String url,String json){
    HttpHeaders headers=new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
    HttpEntity<String> httpEntity=new HttpEntity<>(json,headers);
    return lbRestTemplate.postForEntity(url,httpEntity,String.class).getBody();
  }
  public <T> T doPostJson(String url,String json,TypeReference<T> valueTypeRef){
    String result=doPostJson(url,json);
    try {
      return mapper.readValue(result,valueTypeRef);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  private static HttpEntity<MultiValueMap<String, Object>> getMultiValueMap(HttpHeaders headers, List<File> files, Map<String, String> params) {
    MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
    if (files != null && files.size() > 0) {
      headers.set(HttpHeaders.CONTENT_TYPE, ContentType.MULTIPART_FORM_DATA.getMimeType());
      for (File file : files) {
        multiValueMap.add("file", new org.springframework.core.io.FileSystemResource(file));
      }
    } else {
      headers.set(HttpHeaders.CONTENT_TYPE,ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
    }
    if (params != null && params.size() > 0) {
      for (Map.Entry<String, String> entry : params.entrySet()) {
        multiValueMap.add(entry.getKey(), entry.getValue());
      }
    }
    return new HttpEntity<>(multiValueMap, headers);
  }
}
