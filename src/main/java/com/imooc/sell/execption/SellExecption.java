package com.imooc.sell.execption;

import com.imooc.sell.enums.ResultTypeInfoEnum;

/**
 * 异常类
 * @author 向亚林
 * 2018/3/26 21:21
 */
public class SellExecption extends RuntimeException {
    private Integer code;

    public SellExecption(ResultTypeInfoEnum resultTypeInfoEnum) {
        super(resultTypeInfoEnum.getMessage());
        this.code = resultTypeInfoEnum.getCode();
    }
}
