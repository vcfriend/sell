package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.converter.OrderForm2OrderDTOConverter;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultTypeInfoEnum;
import com.imooc.sell.execption.SellExecption;
import com.imooc.sell.form.OrderForm;
import com.imooc.sell.service.BuyerOrderService;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import utils.ResultVOUtil;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买家订单
 *
 * @author 向亚林
 * 2018/3/28 10:23
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerOrderService buyerOrderService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[创建订单] 参数不正确, orderForm={}", orderForm);
            throw new SellExecption(ResultTypeInfoEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        //判断转换后的购物车数据是否为空
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[创建订单] 购物车不能为空");
            throw new SellExecption(ResultTypeInfoEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());
        return ResultVOUtil.success(map);
    }

    /**
     * 订单列表
     *
     * @param openid 微信openid
     * @param page   分页显示的页数,0为第一页
     * @param size   第页显示的条数
     * @return
     */
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("[查询订单] openid为空");
            throw new SellExecption(ResultTypeInfoEnum.PARAM_ERROR);
        }
        Page<OrderDTO> orderDTOPage = orderService.findAllByOpenid(openid, new PageRequest(page, size));

        //返回封装的订单列表
        return ResultVOUtil.success(orderDTOPage.getContent());
    }


    /**
     * 查询订单详情
     * GET /sell/buyer/order/detail
     *
     * @param openid  微信openid
     * @param orderId 订单orderId
     * @return
     */
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        OrderDTO orderOne = buyerOrderService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderOne);
    }

    /**
     * 取消订单
     * POST /sell/buyer/order/cancel
     *
     * @param openid  微信openid
     * @param orderId 订单orderId
     * @return
     */
    @GetMapping("/cancel")
    public ResultVO<OrderDTO> cancel(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        buyerOrderService.cancelOrder(openid, orderId);

        return ResultVOUtil.success();
    }
}
