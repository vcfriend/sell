package com.imooc.xsell.exception;

import com.imooc.xsell.ResultEnum.ResultEnum;
import lombok.NoArgsConstructor;

/**
 * @author 亚林
 * date 19/10/7,0007 10:47
 */
@NoArgsConstructor
public class SellException extends RuntimeException {
  private Integer code;

  public SellException(ResultEnum resultEnum) {
    super(resultEnum.getMessage());
    this.code = resultEnum.getCode();
  }

  public SellException(Integer code, String defaultMessage) {
    super(defaultMessage);
    this.code = code;
  }
}
