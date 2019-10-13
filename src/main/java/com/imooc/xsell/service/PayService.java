package com.imooc.xsell.service;

import com.imooc.xsell.dto.BuyerOrderDto;

/**
 * 支付
 *
 * @author 亚林
 * date 19/10/13,0013 17:13
 */
public interface PayService {

  void create(BuyerOrderDto buyerOrderDto);
}
