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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 亚林
 * date 19/10/7,0007 10:23
 */
@Slf4j
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
  @Transactional
  public BuyerOrderDto cancel(BuyerOrderDto buyerOrderDto) {
    //判断订单状态是否是新建订单
    if (!buyerOrderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
      log.error("[取消订单] 订单状态不正确, orderId={},orderStatus={}", buyerOrderDto.getOrderId(), buyerOrderDto.getOrderStatus());
      throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
    }
    //修改订单状态
    buyerOrderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
    OrderMaster orderMaster = new OrderMaster();
    BeanUtils.copyProperties(buyerOrderDto, orderMaster);
    OrderMaster updateOrderMaster = orderMasterRepository.save(orderMaster);
    if (updateOrderMaster == null) {
      log.error("[取消订单] 修改订单状态失败,orderMaster={}", orderMaster);
      throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
    }

    //返回库存
    if (buyerOrderDto.getOrderDetailList().isEmpty()) {
      log.error("[取消订单] 订单中无商品详情, buyerOrderDto={}", buyerOrderDto);
      throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
    }
    List<CartDto> cartDtoList = buyerOrderDto.getOrderDetailList().stream().map(orderDetail ->
            new CartDto(orderDetail.getProductId(), orderDetail.getProductQuantity()))
            .collect(Collectors.toList());
    productService.increaseStock(cartDtoList);
    
    //如果已支付,需要退款
    if (buyerOrderDto.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
      //TODO 取消已支付订单
    }
    return buyerOrderDto;
  }

  /**
   * 完结订单
   *
   * @param buyerOrderDto 买家订单dto
   */
  @Override
  @Transactional
  public BuyerOrderDto finish(BuyerOrderDto buyerOrderDto) {
    //判断订单状态
    if (!buyerOrderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
      log.error("[完结订单] 订单状态不正确 orderId={},orderStatus={} ", buyerOrderDto.getOrderId(), buyerOrderDto.getOrderStatus());
      throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
    }
    //修改订单状态
    buyerOrderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
    OrderMaster orderMaster = new OrderMaster();
    BeanUtils.copyProperties(buyerOrderDto, orderMaster);
    OrderMaster save = orderMasterRepository.save(orderMaster);
    if (save == null) {
      log.error("[完结订单] 更新失败, orderMaster={} ", orderMaster);
      throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
    }
    return buyerOrderDto;
  }

  /**
   * 支付订单
   *
   * @param buyerOrderDto 买家订单dto
   */
  @Override
  public BuyerOrderDto pdid(BuyerOrderDto buyerOrderDto) {
    //判断订单状态
    if (!buyerOrderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
      log.error("[订单支付完成] 订单状态不正确, orderId={},orderStatus={} ", buyerOrderDto.getOrderId(), buyerOrderDto.getOrderStatus());
      throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
    }
    //判断支付状态
    if (!buyerOrderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
      log.error("[订单支付完成] 订单支付状态不正确, PayStatus={} ", buyerOrderDto.getPayStatus());
      throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
    }
    //修改支付状态
    buyerOrderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
    OrderMaster orderMaster = new OrderMaster();
    BeanUtils.copyProperties(buyerOrderDto, orderMaster);
    if (orderMasterRepository.save(orderMaster) == null) {
      log.error("[订单支付完成] 更新失败, orderMaster={} ", orderMaster);
      throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
    }
    
    return buyerOrderDto;
  }
}
