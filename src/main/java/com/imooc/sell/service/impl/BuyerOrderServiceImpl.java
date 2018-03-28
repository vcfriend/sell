package com.imooc.sell.service.impl;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultTypeInfoEnum;
import com.imooc.sell.execption.SellExecption;
import com.imooc.sell.service.BuyerOrderService;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 向亚林
 * 2018/3/28 14:24
 */
@Service
@Slf4j
public class BuyerOrderServiceImpl implements BuyerOrderService {
    @Autowired
    OrderService orderService;

    /**
     * 查询一个订单
     *
     * @param openid  微信openid
     * @param orderId 订单id
     * @return 订单DTO
     */
    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        OrderDTO one = orderService.findOne(orderId);
        //对查询结果进行校验
        if (one == null) {
            log.error("[查询订单] 查询订单失败, openid={}", orderId);
            throw new SellExecption(ResultTypeInfoEnum.ORDER_NOT_EXIST);
        }
        //判断是否是自己的订单
        if (!one.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("[查询订单] 订单openid不一致. openid={}, orderDTO={}", orderId, one);
            throw new SellExecption(ResultTypeInfoEnum.ORDER_OWNER_ERROR);
        }

        return one;
    }

    /**
     * 取消一个订单
     *
     * @param openid  微信id
     * @param orderId 订单id
     * @return 订单DTO
     */
    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderOne = findOrderOne(openid, orderId);
        if (orderOne == null) {
            log.error("[查询订单] 查询订单失败, openid={}", orderId);
            throw new SellExecption(ResultTypeInfoEnum.ORDER_NOT_EXIST);
        }

        return orderService.cancel(orderOne);
    }
}
