package com.imooc.xsell.service;

import com.imooc.xsell.dto.BuyerOrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 买家订单service
 *
 * @author 亚林
 * date 19/10/7,0007 9:59
 */
public interface OrderService {
  /**
   * 创建订单
   * @param buyerOrderDto 买家订单dto
   * @return 买家订单
   */
  BuyerOrderDto create(BuyerOrderDto buyerOrderDto);

  /**
   * 查询单个订单
   * @param orderId 订单id
   * @return 买家订单
   */
  BuyerOrderDto findOne(String orderId);

  /**
   * @param buyerOpenid 买家微信openid
   * @param pageable    分页
   * @return 买家订单dto分页信息
   */
  Page<BuyerOrderDto> findList(String buyerOpenid, Pageable pageable);


  /** 取消订单 */
  BuyerOrderDto cancel(BuyerOrderDto buyerOrderDto);

  /** 完结订单 */
  BuyerOrderDto finish(BuyerOrderDto buyerOrderDto);

  /** 支付订单 */
  BuyerOrderDto pdid(BuyerOrderDto buyerOrderDto);
  
}
