package com.imooc.sell.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 向亚林
 * 2018/5/31 11:16
 */
@Data
public class CategoryFrom {
    /** 类目id. */
    private Integer categoryId;
    /** 类目名称 */
    @NotNull(message = "类目名称不能为空")
    private String categoryName;
    /** 类目编号 */
    @NotNull(message = "类目编号不能为空")
    private Integer categoryType;
}
