package com.imooc.xsell.utils;

import com.imooc.xsell.VO.ResultVO;

/**
 * @author 亚林
 * date 19/10/5,0005 20:16
 */
public class ResultVOUtil {
  public static ResultVO<Object> success(Object object) {
    ResultVO<Object> resultVO = new ResultVO<>();
    resultVO.setCode(0);
    resultVO.setMsg("成功");
    resultVO.setData(object);
    return resultVO;
  }

  public static ResultVO<Object> success() {
    return success(null);
  }

  public static ResultVO error(Integer code, String msg) {
    ResultVO resultVO = new ResultVO();
    resultVO.setCode(code);
    resultVO.setMsg(msg);
    return resultVO;
  }
}
