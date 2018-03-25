package com.imooc.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * JSONView返回
 * 商品包含类目(包含商品详情)
 *
 * @author 向亚林
 * 2018/3/25 20:24
 */
@Data
public class ProductCategoryVO {

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
