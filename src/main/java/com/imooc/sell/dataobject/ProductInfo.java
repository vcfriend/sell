package com.imooc.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author 向亚林
 * 2018/3/25 10:46
 */
@Entity
@Data
public class ProductInfo {
    /** 商品ID */
    @Id
    private String productId;
    /** 商品名称 */
    private String productName;
    /** 商品价格 */
    private BigDecimal productPrice;
    /** 商品库存 */
    private Integer productStock;
    /** 商品描述 */
    private String productDescription;
    /** 商品状态: 0正常1下架 */
    private Integer productStatus;
    /** 商品图标 */
    private String productIcon;
    /** 商品类目 */
    private Integer categoryType;


}
