package com.imooc.sell.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imooc.sell.enums.ProductStatusEnum;
import lombok.Data;
import org.apache.commons.lang3.EnumUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

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
    /** 商品状态: 0正常1下架 默认下架*/
    private Integer productStatus = ProductStatusEnum.DOWN.getCode();
    /** 商品图标 */
    private String productIcon;
    /** 商品类目 */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;
    /**获取商品状态的枚举*/
    @JsonIgnore
    public ProductStatusEnum getProductStatus() {
        return EnumUtils.getEnumList(ProductStatusEnum.class).get(productStatus);
    }

}
