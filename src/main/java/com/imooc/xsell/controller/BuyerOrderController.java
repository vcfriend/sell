package com.imooc.xsell.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.imooc.xsell.ResultEnum.ResultEnum;
import com.imooc.xsell.VO.ResultVO;
import com.imooc.xsell.dataobject.OrderDetail;
import com.imooc.xsell.dto.BuyerOrderDto;
import com.imooc.xsell.enums.PayStatusEnum;
import com.imooc.xsell.exception.SellException;
import com.imooc.xsell.form.OrderForm;
import com.imooc.xsell.service.BuyerService;
import com.imooc.xsell.service.OrderService;
import com.imooc.xsell.service.PayService;
import com.imooc.xsell.utils.ResultVOUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

  private final BuyerService buyerService;

  private final PayService payService;

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
  @GetMapping("/list")
  public ResultVO<List<BuyerOrderDto>> list(@RequestParam("openid") String openid,
                                            @RequestParam(value = "page", defaultValue = "0") Integer page,
                                            @RequestParam(value = "size", defaultValue = "10") Integer size) {
    if (openid.isEmpty()) {
      log.error("[查询订单列表] openid为空");
      throw new SellException(ResultEnum.PARAM_ERROR);
    }
    Page<BuyerOrderDto> buyerOrderDtoPage = orderService.findList(openid, new PageRequest(page, size));

    return ResultVOUtil.success(buyerOrderDtoPage.getContent());
  }

  /*查询订单详情*/
  @GetMapping("/detail")
  public ResultVO<BuyerOrderDto> detail(@RequestParam("openid") String openid,
                                        @RequestParam("orderId") String orderId) {
    BuyerOrderDto orderOne = buyerService.findOrderOne(openid, orderId);
    
    return ResultVOUtil.success(orderOne);
  }

  /*取消订单*/
  @PostMapping("/cancel")
  public ResultVO cancel(@RequestParam("openid") String openid,
                         @RequestParam("orderId") String orderId) {
    BuyerOrderDto buyerOrderDto = buyerService.cancelOrder(openid, orderId);

    //如果已支付,需要退款
    if (buyerOrderDto.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
      payService.refund(buyerOrderDto);
    }

    return ResultVOUtil.success();
  }
}
