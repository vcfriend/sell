package com.imooc.xsell.service;

import com.imooc.xsell.dto.BuyerOrderDto;

/**
 * @author 亚林
 * date 19/10/10,0010 20:08
 */
public interface BuyerService {


  /**
   * @param openid 买家微信openid
   * @param orderId 订单id
   * @return 查询买家的一个订单信息
   */
  BuyerOrderDto findOrderOne(String openid, String orderId);

  /**
   * @param openid  买家微信openid
   * @param orderId 订单id
   * @return 取消买家的一个订单
   */
  BuyerOrderDto cancelOrder(String openid, String orderId);
}
