package com.imooc.xsell.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 亚林
 * date 19/10/6,0006 16:25
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum OrderStatusEnum {
  NEW(0, "新下单"),
  FINISHED(1, "完结"),
  CANCEL(2, "已取消")
  ;
  /** 状态码 */
  private Integer code;
  /** 状态信息 */
  private String message;
  
}
