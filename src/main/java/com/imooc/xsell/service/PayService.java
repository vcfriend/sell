package com.imooc.xsell.service;

import com.imooc.xsell.dto.BuyerOrderDto;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

/**
 * 支付
 *
 * @author 亚林
 * date 19/10/13,0013 17:13
 */
public interface PayService {

  /**
   * 支付
   * @param buyerOrderDto 买家订单dto
   * @return 支付时的同步返回参数
   */
  PayResponse create(BuyerOrderDto buyerOrderDto);

  /**
   * 异步通知
   * @param notifyData 异步通知信息
   * @return 支付时的同步返回参数
   */
  PayResponse notify(String notifyData);

  /**
   * 退款
   * @param buyerOrderDto 买家订单dto
   * @return 退款返回的信息
   */
  RefundResponse refund(BuyerOrderDto buyerOrderDto);
}
