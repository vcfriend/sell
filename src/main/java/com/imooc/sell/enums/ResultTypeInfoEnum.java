package com.imooc.sell.enums;

import lombok.Getter;

/**
 * 异常信息返回类型
 *
 * @author 向亚林
 * 2018/3/26 21:23
 */
@Getter
public enum ResultTypeInfoEnum {
    PARAM_ERROR(0, "参数不正确"),
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存不正确"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDER_DETAIL_NOT_EXIST(13, "订单详情不存在"),
    ORDER_STATUS_ERROR(14, "订单状态不正确"),
    ORDER_UPDATE_FLASE(15, "订单更新失败"),
    ORDER_DETAIL_EMPTY(16, "订单详情为空"),
    ORDER_PAY_STATUS_ERROR(17, "订单支付状态错误"),
    CART_EMPTY(18,"购物车为空" ),
    ORDER_OWNER_ERROR(19, "订单openid错误"),
    WECHAT_MP_ERROR(20,"微信公众号错误"),
    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(21, "微信异步通知金额验证不通知"),
    ORDER_CANCEL_SUCCESS(22, "订单取消成功"),
    ORDER_FINISH_SUCCESS(23, "订单完结成功"),
    PRODUCT_STATUS_ERROR(24, "商品状态不正确");

    private Integer code;

    private String message;

    ResultTypeInfoEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
