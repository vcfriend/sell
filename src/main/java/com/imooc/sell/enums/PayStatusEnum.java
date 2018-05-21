package com.imooc.sell.enums;

import lombok.Getter;

/**
 * 支付状态枚举
 *
 * @author 向亚林
 * 2018/3/26 15:50
 */
@Getter
public enum PayStatusEnum implements CodeEnum {
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
