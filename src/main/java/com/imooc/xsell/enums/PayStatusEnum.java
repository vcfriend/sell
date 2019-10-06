package com.imooc.xsell.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 亚林
 * date 19/10/6,0006 16:37
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PayStatusEnum {
  WAIT(0, "等待支付"),
  SUCCESS(1, "支付成功"),
  ;
  /** 状态码 */
  private Integer code;
  /** 状态信息 */
  private String message;
  
}
