package com.imooc.xsell.service.impl;

import com.imooc.xsell.ResultEnum.ResultEnum;
import com.imooc.xsell.dto.BuyerOrderDto;
import com.imooc.xsell.exception.SellException;
import com.imooc.xsell.service.OrderService;
import com.imooc.xsell.service.PayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付
 * @author 亚林
 * date 19/10/13,0013 17:13
 */
@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PayServiceImpl implements PayService {

  private static final String ORDER_NAME = "微信点餐订单";

  private final BestPayServiceImpl bestPayService;

  private final OrderService orderService;
  
  @Override
  public PayResponse create(BuyerOrderDto buyerOrderDto) {
    PayRequest payRequest = new PayRequest();
    payRequest.setOpenid(buyerOrderDto.getBuyerOpenid());
    payRequest.setOrderAmount(buyerOrderDto.getOrderAmount().doubleValue());
    payRequest.setOrderName(buyerOrderDto.getOrderId());
    payRequest.setOrderName(ORDER_NAME);
    payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
    log.info("[微信支付] request={}", JsonUtil.toJson(payRequest));
    
    PayResponse payResponse = bestPayService.pay(payRequest);
    log.info("[微信支付 response={}", JsonUtil.toJson(payResponse));
    return payResponse;
  }

  @Override
  public PayResponse notify(String notifyData) {
    //1. 验证签名
    //2. 支付的状态
    //3. 支付金额
    //4. 支付人(下单人 == 支付人)
    PayResponse payResponse = bestPayService.asyncNotify(notifyData);
    log.info("[微信支付] 异步通知, payResponse={}", JsonUtil.toJson(payResponse));
    
    //查询订单
    BuyerOrderDto orderDto = orderService.findOne(payResponse.getOrderId());
    if (orderDto == null) {
      log.error("[微信支付] 异步通知,订单不存在, orderId={}", payResponse.getOrderId());
      throw new SellException(ResultEnum.ORDER_NOT_EXIST);
    }
    //判断金额是否一致(0.10 == 0.1)
    if (Math.abs(payResponse.getOrderAmount() - orderDto.getOrderAmount().doubleValue()) > 0.01) {
      log.error("[微信支付] 异步通知,订单金额不一致, orderId={}, 微信通知金额={}, 系统金额={}",
              payResponse.getOrderId(), payResponse.getOrderAmount(), orderDto.getOrderAmount());
      throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
    }
    
    //修改订单支付状态
    orderService.pdid(orderDto);
    
    return payResponse;
  }

  @Override
  public RefundResponse refund(BuyerOrderDto buyerOrderDto) {
    RefundRequest refundRequest = new RefundRequest();
    refundRequest.setOrderId(buyerOrderDto.getOrderId());
    refundRequest.setOrderAmount(buyerOrderDto.getOrderAmount().doubleValue());
    refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
    log.info("【微信退款】request={}", JsonUtil.toJson(refundRequest));

    RefundResponse refundResponse = bestPayService.refund(refundRequest);
    log.info("【微信退款】response={}", JsonUtil.toJson(refundResponse));

    return refundResponse;
  }
}

