package vip.codehome.gateway.domain;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @author dsys
 * @version v1.0
 */
@Table(name = "ROUTE_DEFINITION")
@Data
public class RouteDefinitionDO {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String id;

  private String routeId;
  private Integer order;
  private String uri;
  private String predicates;
  private String filters;
  private Boolean status;
  private Date createTime;
  private Date updateTime;
}
