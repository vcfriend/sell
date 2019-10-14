package com.imooc.xsell.service;

import com.imooc.xsell.dto.BuyerOrderDto;
import com.lly835.bestpay.model.PayResponse;

/**
 * 支付
 *
 * @author 亚林
 * date 19/10/13,0013 17:13
 */
public interface PayService {

  PayResponse create(BuyerOrderDto buyerOrderDto);

  PayResponse notify(String notifyData);
}
