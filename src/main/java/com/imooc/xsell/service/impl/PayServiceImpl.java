package com.imooc.xsell.service.impl;

import com.imooc.xsell.dto.BuyerOrderDto;
import com.imooc.xsell.service.PayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
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
  
  @Override
  public void create(BuyerOrderDto buyerOrderDto) {
    PayRequest payRequest = new PayRequest();
    payRequest.setOpenid(buyerOrderDto.getBuyerOpenid());
    payRequest.setOrderAmount(buyerOrderDto.getOrderAmount().doubleValue());
    payRequest.setOrderName(buyerOrderDto.getOrderId());
    payRequest.setOrderName(ORDER_NAME);
    payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
    log.info("[微信支付] request={}", JsonUtil.toJson(payRequest));
    
    PayResponse payResponse = bestPayService.pay(payRequest);
    log.info("[微信支付 response={}", JsonUtil.toJson(payResponse));
  }
}
