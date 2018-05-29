package com.imooc.sell.controller;

import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家端商品
 *
 * @author 向亚林
 * 2018/5/29 11:18
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {
    @Autowired
    private ProductInfoService productInfoService;

    /**
     * 商品列表
     * @param page 当前页
     * @param size 每页显示条数
     * @param map 查询结果
     * @return 返回结果到视图层
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(pageRequest);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("/product/list", map);
    }
}
