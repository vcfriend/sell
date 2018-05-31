package com.imooc.sell.controller;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.execption.SellExecption;
import com.imooc.sell.form.CategoryFrom;
import com.imooc.sell.service.ProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 卖家类目
 *
 * @author 向亚林
 * 2018/5/31 10:54
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private ProductCategoryService categoryService;

    /**
     * 类目列表
     *
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    /**
     * 展示单个类目
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {
        if (categoryId != null) {
            ProductCategory category = categoryService.findOne(categoryId);
            map.put("category", category);
        }
        return new ModelAndView("/category/index", map);
    }
    /**
     * 新增或更新类目
     * @param from 前端类目表单属性的封装类
     * @param bindingResult 表单验证信息
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryFrom from,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        //验证表单填写是否有错
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/seller/category/index");
        }
        ProductCategory productCategory = new ProductCategory();
        try {
            if (from.getCategoryId() != null) {
                productCategory = categoryService.findOne(from.getCategoryId());
            }

            BeanUtils.copyProperties(from, productCategory);
            categoryService.save(productCategory);
        } catch (SellExecption e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/category/index");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/seller/category/list");
        return new ModelAndView("/common/success", map);
    }

}
