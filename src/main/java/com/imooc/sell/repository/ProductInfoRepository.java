package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 向亚林
 * 2018/3/25 11:16
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    /**
     * 按商品状态查询商品集合
     * @param productStatus 商品状态
     * @return 商品信息集合
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
