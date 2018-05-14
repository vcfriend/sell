package com.imooc.sell.service.impl;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultTypeInfoEnum;
import com.imooc.sell.execption.SellExecption;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.MathUtil;

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
    @Autowired
    private OrderService orderService;

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
        //1. 验证签名
        //2. 验证支付的状态
        //3. 验证支付的金额
        //4. 验证支付人(下单人==支付人)

        //调用bestPayServic完成前两部验证,验证签名和支付状态
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("[微信支付] 异步通知, payResponse={}", JsonUtil.toJson(payResponse));

        //查询订单签证支付金额或支付人
        OrderDTO one = orderService.findOne(payResponse.getOrderId());
        //判断订单是否存在
        if (one == null) {
            log.info("[微信支付] 异步通知, 订单不存在, orderId={}", payResponse.getOrderId());
            throw new SellExecption(ResultTypeInfoEnum.ORDER_NOT_EXIST);
        }
        //判断订单金额与支付金额是否一致(如: 0.1, 0.10, 0.100000000123)
        if (!MathUtil.moneyEquals(one.getOrderAmount().doubleValue(), payResponse.getOrderAmount())) {
            log.info("[微信支付] 异步通知, 订单金额不一致, orderId={},微信通知金额={}, 系统订单金额={}",
                    payResponse.getOrderId(), payResponse.getOrderAmount(), one.getOrderAmount());
            throw new SellExecption(ResultTypeInfoEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        //修改订单状态
        orderService.paid(one);
        return payResponse;
    }

    /**
     * 微信退款
     *
     * @param orderDTO 订单
     * @return
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信退款] request={}", JsonUtil.toJson(refundRequest));
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("[微信退款] response={}", JsonUtil.toJson(refundResponse));

        return refundResponse;
    }
}
