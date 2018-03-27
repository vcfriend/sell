package com.imooc.sell.dto;

import lombok.Data;

/**
 * 财物项 DTO
 * 展示订单 DTO 的详情列表
 * @author 向亚林
 * 2018/3/26 21:06
 */
@Data
public class CartDTO {
    /** 商品Id */
    private String productId;

    /** 购买数量 */
    private Integer productQuanttity;

    public CartDTO(String productId, Integer productQuanttity) {
        this.productId = productId;
        this.productQuanttity = productQuanttity;
    }
}
