package com.imooc.xsell.controller;

import com.imooc.xsell.VO.ProductInfoVO;
import com.imooc.xsell.VO.ProductVO;
import com.imooc.xsell.VO.ResultVO;
import com.imooc.xsell.dataobject.ProductCategory;
import com.imooc.xsell.dataobject.ProductInfo;
import com.imooc.xsell.service.CategoryService;
import com.imooc.xsell.service.ProductService;
import com.imooc.xsell.utils.ResultVOUtil;
import com.sun.istack.internal.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 * @author 亚林
 * date 19/10/5,0005 16:48
 */
@RestController
@RequestMapping("/buyer/product")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BuyerProductController {
  private final ProductService productService;
  private final CategoryService categoryService;

  @GetMapping("test")
  public ResultVO test() {
    return ResultVOUtil.success();
  }
  @GetMapping("/list")
  public ResultVO list(){
    //1.查询所有上架商品
    List<ProductInfo> productInfoList = productService.findUpAll();
    //2.查询类目
//    List<Integer> categoryTypeList = new ArrayList<>();
    //传统方法
//    for (ProductInfo productInfo : productInfoList) {
//      categoryTypeList.add(productInfo.getCategoryType());
//    }
    //精简方法(java8 lambda)
    List<Integer> categoryTypeList = productInfoList.stream()
            .map(ProductInfo::getCategoryType)
            .collect(Collectors.toList());
    System.out.println("查询所有上架的商品");
    productInfoList.forEach(System.out::println);
    //查询上架商品类目信息
    List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
    System.out.println("查询所有类目信息");
    productCategoryList.forEach(System.out::println);
    
    //3.数据拼装
    List<ProductVO> productVOList = new ArrayList<>();
    //拼接商品类目信息
    for (ProductCategory productCategory : productCategoryList) {
      ProductVO productVO = new ProductVO();
      productVO.setCategoryName(productCategory.getCategoryName());
      productVO.setCategoryType(productCategory.getCategoryType());
      //拼接商品详细
      List<ProductInfoVO> productInfoVOList = new ArrayList<>();
      for (ProductInfo productInfo : productInfoList) {
        if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
          ProductInfoVO productInfoVO = new ProductInfoVO();
          //将商品详细复制到VO实体用
          BeanUtils.copyProperties(productInfo, productInfoVO);
          productInfoVOList.add(productInfoVO);
        }
      }
      productVO.setProductInfoVOList(productInfoVOList);
      productVOList.add(productVO);
    }
    return ResultVOUtil.success(productVOList);
  }

}
