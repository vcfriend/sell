package com.imooc.sell.VO;

import lombok.Data;

/**
 * http返回的最外层对象
 *
 * @author 向亚林
 * 2018/3/25 19:03
 */
@Data
public class ResultVO<T> {
    /**错误码 */
    private Integer code;
    /**错误信息*/
    private String message;
    /**返回的具体内容*/
    private T data;
}
