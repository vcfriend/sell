package com.imooc.sell.controller;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.execption.SellExecption;
import com.imooc.sell.form.ProductForm;
import com.imooc.sell.service.ProductCategoryService;
import com.imooc.sell.service.ProductInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import utils.KeyUtil;

import javax.validation.Valid;
import java.util.List;
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
    @Autowired
    private ProductCategoryService productCategoryService;
    @Value("${server.context-path}")
    private String contextPath;

    /**
     * 商品列表
     *
     * @param page 当前页
     * @param size 每页显示条数
     * @param map  查询结果
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

    /**
     * 商品上架
     *
     * @param productId 商品id
     * @param map       查询结果
     * @return 返回结果到视图层
     */
    @RequestMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
            productInfoService.onSale(productId);
        } catch (SellExecption e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/product/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/seller/product/list");
        return new ModelAndView("/common/success", map);
    }

    @RequestMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String, Object> map) {
        try {
            productInfoService.offSale(productId);
        } catch (SellExecption e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/product/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("url", "/seller/product/list");
        return new ModelAndView("/common/success", map);
    }

    /**
     * 新增修改页面
     *
     * @param productId 商品id 非必须
     * @param map       查询结果
     * @return 返回结果到视图层
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map) {
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productInfoService.findOne(productId);
            map.put("productInfo", productInfo);
        }
        //查询商品类目
        List<ProductCategory> categoryList = productCategoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("/product/index", map);
    }

    /**
     * 更新/保存商品
     * @param form 表单封装属性
     * @param bindingResult 表单验证信息
     * @param map 返回信息
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        ProductInfo produtInfo = new ProductInfo();
        try {
            if (!StringUtils.isEmpty(form.getProductId())) {
                //商品Id不为空时,查询Id查询出商品信息
                produtInfo = productInfoService.findOne(form.getProductId());
            } else {
                //商品id不存在时,为新增商品,且为商品生成id
                form.setProductId(KeyUtil.generateUniqueKey());
            }
            BeanUtils.copyProperties(form, produtInfo);
            productInfoService.save(produtInfo);
        } catch (SellExecption e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/seller/product/index");
        return new ModelAndView("common/success", map);
    }
}
