package com.imooc.xsell.VO;

import lombok.Data;

/**
 * @author 亚林
 * date 19/10/5,0005 17:15
 */
@Data
public class ResultVO<T> {
  /**
   * 错误码
   */
  private Integer code;
  /**
   * 提示信息
   */
  private String msg;
  /**
   * 具体内容
   */
  private T data;
}
