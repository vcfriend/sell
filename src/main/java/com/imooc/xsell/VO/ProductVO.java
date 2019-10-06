package com.imooc.xsell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品类目前端接口数据
 * @author 亚林
 * date 19/10/5,0005 17:56
 */
@Data
public class ProductVO {
  @JsonProperty("name")
  private String categoryName;
  @JsonProperty("type")
  private Integer categoryType;
  @JsonProperty("foods")
  private List<ProductInfoVO> productInfoVOList;
}
