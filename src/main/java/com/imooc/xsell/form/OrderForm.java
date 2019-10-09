package com.imooc.xsell.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author 亚林
 * date 19/10/9,0009 10:54
 */
@Data
public class OrderForm {
  /** 买家姓名 */
  @NotNull(message = "姓名必填")
  private String name;

  /** 买家手机号 */
  @NotNull(message = "手机号必填")
  private String phone;

  /** 买家地址 */
  @NotNull(message = "地址必填")
  private String address;

  /** 买家微信openid */
  @NotNull(message = "openid必填")
  private String openid;

  /** 购物车 */
  @NotNull(message = "购物车不能为空")
  private String items;
  
}
