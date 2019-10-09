package com.imooc.xsell.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.imooc.xsell.ResultEnum.ResultEnum;
import com.imooc.xsell.VO.ResultVO;
import com.imooc.xsell.dataobject.OrderDetail;
import com.imooc.xsell.dto.BuyerOrderDto;
import com.imooc.xsell.exception.SellException;
import com.imooc.xsell.form.OrderForm;
import com.imooc.xsell.service.OrderService;
import com.imooc.xsell.utils.ResultVOUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买家订单
 *
 * @author 亚林
 * date 19/10/9,0009 10:44
 */
@Slf4j
@RestController
@RequestMapping("/buyer/order")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BuyerOrderController {
  
  private final OrderService orderService;

  /** 创建订单 */
  @PostMapping("/create")
  public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      log.error("[创建订单] 参数不正确, orderForm={} ", orderForm);
      throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
    }
    /*将json中的数据转存到dto中*/
    BuyerOrderDto buyerOrderDto = new BuyerOrderDto()
            .setBuyerName(orderForm.getName())
            .setBuyerPhone(orderForm.getPhone())
            .setBuyerAddress(orderForm.getAddress())
            .setBuyerOpenid(orderForm.getOpenid());
//    /*使用Gson解析把json中的list列表数据转换成list对象*/
//    Gson gson = new Gson();
//    List<OrderDetail> orderDetailList;
//    try {
//      orderDetailList = gson.fromJson(orderForm.getItems(),
//              new TypeToken<List<OrderDetail>>() {
//              }.getType());
//    } catch (JsonSyntaxException e) {
//      log.error("[json对象转换] 错误, string={} ",orderForm.getItems());
//      throw new SellException(ResultEnum.PARAM_ERROR);
//    }

    /*使用fastjson 把json字符串数组转换成list*/
    List<OrderDetail> orderDetailList = JSON.parseArray(orderForm.getItems(), OrderDetail.class);
    if (orderDetailList == null) {
      log.error("[创建订单] 购物车不能为空");
      throw new SellException(ResultEnum.CART_NOT_EMPTY);
    }
    buyerOrderDto.setOrderDetailList(orderDetailList);
    BuyerOrderDto createResult = orderService.create(buyerOrderDto);

    HashMap<String, String> map = new HashMap<>();
    map.put("orderId", createResult.getOrderId());

    return ResultVOUtil.success(map);
  }
  
  /*查询订单列表*/
  
  /*查询订单详情*/
  
  /*取消订单*/
  
}
