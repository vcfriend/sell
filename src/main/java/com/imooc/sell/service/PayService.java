package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

/**
 * @author 向亚林
 * 2018/4/15 11:28
 */
public interface PayService {

    /**
     * 创建预支付订单
     * @param orderDTO
     * @return
     */
    PayResponse create(OrderDTO orderDTO);

    /**
     * 微信支付异步通知
     * @param notifyData
     * @return
     */
    PayResponse notify(String notifyData);

    /**
     * 微信退款
     * @param orderDTO 订单
     * @return
     */
    RefundResponse refund(OrderDTO orderDTO);
}
