package com.imooc.xsell.controller;

import com.imooc.xsell.ResultEnum.ResultEnum;
import com.imooc.xsell.dto.BuyerOrderDto;
import com.imooc.xsell.exception.SellException;
import com.imooc.xsell.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 支付
 *
 * @author 亚林
 * date 19/10/13,0013 16:45
 */
@Controller
@RequestMapping("/pay")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PayController {

  private final OrderService orderService;

  @GetMapping("/create")
  public void create(@RequestParam("orderId") String orderId,
                     @RequestParam("returnUrl") String returnUrl) {
    //1. 查询订单
    BuyerOrderDto one = orderService.findOne(orderId);
    if (one == null) {
      throw new SellException(ResultEnum.ORDER_NOT_EXIST);
    }
    //2. 发起支付
  }
  
}
