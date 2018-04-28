package com.imooc.sell.service.impl;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.service.PayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 向亚林
 * 2018/4/15 11:29
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {
    private static final String ORDER_NAME = "微信点餐订单";
    @Autowired
    private BestPayServiceImpl bestPayService;
    /**
     * 创建预支付订单
     * @param orderDTO
     * @return
     */
    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信支付] request={}", JsonUtil.toJson(payRequest));

        PayResponse payResponse = bestPayService.pay(payRequest);

        log.info("[微信支付] response={}", JsonUtil.toJson(payResponse));
        return payResponse;
    }

    /**
     * 微信支付异步通知
     * @param notifyData
     * @return
     */
    @Override
    public PayResponse notify(String notifyData) {
        return null;
    }
}
