package com.imooc.xsell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品详情
 *
 * @author 亚林
 * date 19/10/5,0005 17:58
 */
@Data
public class ProductInfoVO {
  @JsonProperty("id")
  private String productId;
  @JsonProperty("name")
  private String productName;
  @JsonProperty("price")
  private BigDecimal productPrice;
  @JsonProperty("description")
  private String productDescription;
  @JsonProperty("icon")
  private String productIcon;

}
