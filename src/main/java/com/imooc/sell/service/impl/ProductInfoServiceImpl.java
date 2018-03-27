package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.enums.ResultTypeInfoEnum;
import com.imooc.sell.execption.SellExecption;
import com.imooc.sell.repository.ProductInfoRepository;
import com.imooc.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 向亚林
 * 2018/3/25 16:05
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoRepository productInfoRepository;
    /**
     * 根据ID查询
     *
     * @param productId
     * @return
     */
    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.findOne(productId);
    }

    /**
     * 查询所有上架的商品
     *
     * @return
     */
    @Override
    public List<ProductInfo> findAllByUp() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    /**
     * 分页查询所有商品
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    /**
     * 添加或修改商品
     *
     * @param productInfo
     * @return
     */
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    /**
     * 加库存
     *
     * @param cartDTOList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo one = productInfoRepository.findOne(cartDTO.getProductId());
            if (one == null) {
                throw new SellExecption(ResultTypeInfoEnum.PRODUCT_NOT_EXIST);
            }
            Integer productStock = one.getProductStock() + cartDTO.getProductQuanttity();
            one.setProductStock(productStock);
            productInfoRepository.save(one);
        }
    }

    /**
     * 减库存
     *
     * @param cartDTOList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo one = productInfoRepository.findOne(cartDTO.getProductId());
            if (one == null) {
                throw new SellExecption(ResultTypeInfoEnum.PRODUCT_NOT_EXIST);
            }
            Integer stockQuanttity = one.getProductStock() - cartDTO.getProductQuanttity();
            if (stockQuanttity < 0) {
                throw new SellExecption(ResultTypeInfoEnum.PRODUCT_STOCK_ERROR);
            }
            one.setProductStock(stockQuanttity);
            productInfoRepository.save(one);
        }
    }
}
