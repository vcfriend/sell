package com.imooc.xsell.service.impl;

import com.imooc.xsell.ResultEnum.ResultEnum;
import com.imooc.xsell.dataobject.OrderDetail;
import com.imooc.xsell.dataobject.OrderMaster;
import com.imooc.xsell.dataobject.ProductInfo;
import com.imooc.xsell.dto.BuyerOrderDto;
import com.imooc.xsell.dto.CartDto;
import com.imooc.xsell.enums.OrderStatusEnum;
import com.imooc.xsell.enums.PayStatusEnum;
import com.imooc.xsell.exception.SellException;
import com.imooc.xsell.repository.OrderDetailRepository;
import com.imooc.xsell.repository.OrderMasterRepository;
import com.imooc.xsell.service.OrderService;
import com.imooc.xsell.service.ProductService;
import com.imooc.xsell.utils.KeyUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 亚林
 * date 19/10/7,0007 10:23
 */
@Service
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {

  private ProductService productService;
  private OrderDetailRepository orderDetailRepository;
  private OrderMasterRepository orderMasterRepository;

  /**
   * 创建订单
   *
   * @param buyerOrderDto 买家订单dto
   * @return 买家订单
   */
  @Override
  public BuyerOrderDto create(BuyerOrderDto buyerOrderDto) {
//    生成订单id
    String orderId = KeyUtil.genUniqueKey();
//    订单总金额
    BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
    // 1.通过买家订单获取订单详情,查询商品价格
    for (OrderDetail orderDetail : buyerOrderDto.getOrderDetailList()) {
      ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
      if (productInfo == null) {
        throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
      }
      // 2.计算订单总金额
      orderAmount = productInfo.getProductPrice()
              .multiply(new BigDecimal(orderDetail.getProductQuantity()))
              .add(orderAmount);
      
      orderDetail.setDetailId(KeyUtil.genUniqueKey());
      orderDetail.setOrderId(orderId);
      BeanUtils.copyProperties(productInfo, orderDetail);
      orderDetailRepository.save(orderDetail);
      
    }
    
    // 3.写入订单数据
    OrderMaster orderMaster = new OrderMaster();
    BeanUtils.copyProperties(buyerOrderDto, orderMaster);
    orderMaster.setOrderId(orderId);
    orderMaster.setOrderAmount(orderAmount);
    orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
    orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
    orderMasterRepository.save(orderMaster);
    
    // 4.扣库存
    List<CartDto> cartDtoList = buyerOrderDto.getOrderDetailList().stream().map(orderDetail ->
            new CartDto(orderDetail.getProductId(), orderDetail.getProductQuantity()))
            .collect(Collectors.toList());
    productService.decreaseStock(cartDtoList);
    
    return buyerOrderDto;
  }

  /**
   * 查询单个订单
   *
   * @param orderId 订单id
   * @return 买家订单
   */
  @Override
  public BuyerOrderDto findOne(String orderId) {
    OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
    if (orderMaster == null) {
      throw new SellException(ResultEnum.ORDER_NOT_EXIST);
    }
    List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
    if (CollectionUtils.isEmpty(orderDetailList)) {
      throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
    }
    BuyerOrderDto buyerOrderDto = new BuyerOrderDto();
    BeanUtils.copyProperties(orderMaster, buyerOrderDto);
    buyerOrderDto.setOrderDetailList(orderDetailList);
    
    return buyerOrderDto;
  }

  /**
   * @param buyerOpenid 买家微信openid
   * @param pageable    分页
   * @return 买家订单dto分页信息
   */
  @Override
  public Page<BuyerOrderDto> findList(String buyerOpenid, Pageable pageable) {
    Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
//    将orderMasterList转换成buyerOrderDtoList
    List<BuyerOrderDto> buyerOrderDtoList = orderMasterPage.getContent().stream().map(orderMaster -> {
      BuyerOrderDto buyerOrderDto = new BuyerOrderDto();
      BeanUtils.copyProperties(orderMaster, buyerOrderDto);
      return buyerOrderDto;
    }).collect(Collectors.toList());
    return new PageImpl<>(buyerOrderDtoList,pageable,orderMasterPage.getTotalElements());
  }

  /**
   * 取消订单
   *
   * @param buyerOrderDto 买家订单dto
   */
  @Override
  public BuyerOrderDto cancel(BuyerOrderDto buyerOrderDto) {
    return null;
  }

  /**
   * 取消订单
   *
   * @param buyerOrderDto 买家订单dto
   */
  @Override
  public BuyerOrderDto finish(BuyerOrderDto buyerOrderDto) {
    return null;
  }

  /**
   * 支付订单
   *
   * @param buyerOrderDto 买家订单dto
   */
  @Override
  public BuyerOrderDto pdid(BuyerOrderDto buyerOrderDto) {
    return null;
  }
}
