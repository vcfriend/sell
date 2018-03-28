package com.imooc.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 订单的表单属性
 *
 * @author 向亚林
 * 2018/3/28 11:31
 */
@Data
public class OrderForm {
    /**
     * 买家姓名
     */
    @NotEmpty(message = "姓名必填")
    private String name;
    /**
     * 买家手机号
     */
    @NotEmpty(message = "手机号必填写")
    private String phone;
    /**
     * 买家地址
     */
    @NotEmpty(message = "地址必填")
    private String address;
    /**
     * 买家微信Id
     */
    @NotEmpty(message = "openid必填")
    private String openid;
    /**
     * 购物车
     */
    @NotEmpty(message = "购物车必填")
    private String items;
}
