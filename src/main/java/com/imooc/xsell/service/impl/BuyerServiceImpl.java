package com.imooc.xsell.service.impl;

import com.imooc.xsell.ResultEnum.ResultEnum;
import com.imooc.xsell.dto.BuyerOrderDto;
import com.imooc.xsell.exception.SellException;
import com.imooc.xsell.service.BuyerService;
import com.imooc.xsell.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 亚林
 * date 19/10/10,0010 20:14
 */
@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BuyerServiceImpl implements BuyerService {
  
  private final OrderService orderService;
  
  @Override
  public BuyerOrderDto findOrderOne(String openid, String orderId) {
    return checkOrderOwner(openid, orderId);
  }

  @Override
  public BuyerOrderDto cancelOrder(String openid, String orderId) {
    BuyerOrderDto buyerOrderDto = checkOrderOwner(openid, orderId);
    if (buyerOrderDto == null) {
      log.error("【取消订单】查不到改订单, orderId={}", orderId);
      throw new SellException(ResultEnum.ORDER_NOT_EXIST);
    }
    return orderService.cancel(buyerOrderDto);
  }
  

  /**
   * @param openid  买家微信openid
   * @param orderId 订单id
   * @return 检查订单是否买家本的, 是则返回买家订单信息dto
   */
  private BuyerOrderDto checkOrderOwner(String openid, String orderId) {
    BuyerOrderDto buyerOrderDto = orderService.findOne(orderId);
    if (buyerOrderDto == null) {
      return null;
    }
    //如果不是自己的订单,则抛出异常
    if (!buyerOrderDto.getBuyerOpenid().equalsIgnoreCase(openid)) {
      log.error("[查询订单] 订单的openid不一致. openid={}, buyerOrderDto={} ",openid, buyerOrderDto);
      throw new SellException(ResultEnum.ORDER_OPEND_ID_ERROR);
    }
    return buyerOrderDto;
  }
  
}
