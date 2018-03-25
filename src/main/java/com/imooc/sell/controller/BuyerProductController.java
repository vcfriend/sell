package com.imooc.sell.controller;

import com.imooc.sell.VO.ProductCategoryVO;
import com.imooc.sell.VO.ProductInfoVO;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.service.ProductCategoryService;
import com.imooc.sell.service.ProductInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.ResultVOUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 *
 * @author 向亚林
 * 2018/3/25 19:00
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("list")
    public ResultVO list() {
        ResultVO<ProductCategoryVO> resultVO = new ResultVO();
        //1.查询所有上架的商品
        List<ProductInfo> productInfoList = productInfoService.findAllByUp();
        //2.查询类目
//        List<Integer> categoryTypeList = new ArrayList<>();
//        //传统方法
//        for (ProductInfo productInfo : productInfoList) {
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        //精简方法 (JDK 8 lambda)
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());

        //根据类目编号查询类目
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        //3.数据拼装 返回前端的JSON数据集合
        List<ProductCategoryVO> productCategoryVOList = new ArrayList<>();
        //遍历商品类目
        for (ProductCategory productCategory : productCategoryList) {
            //封装类:商品类目和类目下的商品集合
            ProductCategoryVO productCategoryVO = new ProductCategoryVO();
            productCategoryVO.setCategoryType(productCategory.getCategoryType());
            productCategoryVO.setCategoryName(productCategory.getCategoryName());
            //封装商品详情
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                //收集相同类目的商品
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //将商品详情类实体的信息复制到时它的封装实体
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            //初始化商品类目封装实体下的商品集合
            productCategoryVO.setProductInfoVOList(productInfoVOList);
            productCategoryVOList.add(productCategoryVO);
        }

        return ResultVOUtil.success(productCategoryVOList);
    }
}
