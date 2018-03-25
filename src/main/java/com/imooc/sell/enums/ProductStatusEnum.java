package com.imooc.sell.enums;

import lombok.Getter;

/**
 * 商品状态
 *
 * @author 向亚林
 * 2018/3/25 16:17
 */
@Getter
public enum ProductStatusEnum {
    UP(0,"在架"),
    DOWN(1,"下架");

    private Integer code;
    private String message;

    ProductStatusEnum() {
    }

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
