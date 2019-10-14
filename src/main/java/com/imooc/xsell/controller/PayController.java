package com.imooc.xsell.controller;

import com.imooc.xsell.ResultEnum.ResultEnum;
import com.imooc.xsell.dto.BuyerOrderDto;
import com.imooc.xsell.exception.SellException;
import com.imooc.xsell.service.OrderService;
import com.imooc.xsell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

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
  
  private final PayService payService;

  @GetMapping("/create")
  public ModelAndView create(@RequestParam("orderId") String orderId,
                             @RequestParam("returnUrl") String returnUrl,
                             Map<String, Object> map) {
    //1. 查询订单
    BuyerOrderDto one = orderService.findOne(orderId);
    if (one == null) {
      throw new SellException(ResultEnum.ORDER_NOT_EXIST);
    }
    //2. 发起支付
    PayResponse payResponse = payService.create(one);
    map.put("payResponse", payResponse);
    map.put("returnUrl", returnUrl);

    return new ModelAndView("pay/create", map);
  }

  /**
   * 微信异步通知
   * @param notifyData
   */
  @PostMapping("/notify")
  public ModelAndView notify(@RequestBody String notifyData) {
    payService.notify(notifyData);

    //返回给微信处理结果
    return new ModelAndView("pay/success");
  }
  
}
