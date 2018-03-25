package utils;

import com.imooc.sell.VO.ResultVO;

/**
 * @author 向亚林
 * 2018/3/25 21:48
 */
public class ResultVOUtil {

    public static <T> ResultVO<T> success(T data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setData(data);
        resultVO.setMessage("成功");
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        return resultVO;
    }
}
