package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDTO;

/**
 * 买家
 * @author 向亚林
 * 2018/3/28 14:19
 */
public interface BuyerOrderService {

    /**
     * 查询一个订单
     *
     * @param openid  微信openid
     * @param orderId 订单id
     * @return 订单DTO
     */
    OrderDTO findOrderOne(String openid, String orderId);

    /**
     * 取消一个订单
     * @param openid 微信id
     * @param orderId 订单id
     * @return 订单DTO
     */
    OrderDTO cancelOrder(String openid, String orderId);
}
