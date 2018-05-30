package com.imooc.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 商品提交表单
 *
 * @author 向亚林
 * 2018/5/30 10:47
 */
@Data
public class ProductForm {
    /** 商品ID */
    private String productId;
    /** 商品名称 */
    @NotBlank(message = "名称必填")
    private String productName;
    /** 商品价格 */
    @NotNull(message = "价格必填")
    private BigDecimal productPrice;
    /** 商品库存 */
    @NotNull(message = "库存必填")
    private Integer productStock;
    /** 商品描述 */
    @NotBlank(message = "描述必填")
    private String productDescription;
    /** 商品图标 */
    private String productIcon;
    /** 商品类目 */
    @NotNull(message = "类目必填")
    private Integer categoryType;
}
