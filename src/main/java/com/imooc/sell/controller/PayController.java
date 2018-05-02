package com.imooc.sell.controller;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultTypeInfoEnum;
import com.imooc.sell.execption.SellExecption;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 支付
 *
 * @author 向亚林
 * 2018/4/15 11:15
 */
@Controller
@Slf4j
//@RequestMapping("/pay") //错误在此,多一层路径,导致访问 http://xsell.natapp1.cc/pay  出现404
public class PayController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    /**
     * 微信支付帐号借用
     * 前端支付地址调用回调
     * @param openid 师兄干货处获取的openid
     * @param orderId 订单id
     * @param returnUrl 支付完返回地址
     * @param map 后端支付页面动态参数
     * @return 用户支付页面调用
     */
    @GetMapping("/pay")
    public ModelAndView index(@RequestParam("openid") String openid,
                              @RequestParam("orderId") String orderId,
                              @RequestParam("returnUrl") String returnUrl,
                              Map<String, Object> map) {
        return create(orderId, returnUrl, map);
    }

    @GetMapping("/pay/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map) {
        //1. 查询订单
        OrderDTO one = orderService.findOne(orderId);
        if (one == null) {
            throw new SellExecption(ResultTypeInfoEnum.ORDER_NOT_EXIST);
        }
        //2.创建预支付订单
        PayResponse payResponse = payService.create(one);
        map.put("payResponse", payResponse);
        //用于支付后地址的跳转需要对地址进行URLDecoder.decode编码,
        //用这个还不行地址返回时会带有 http://sell.springboot.cn/sell/前缀 ,且报404错误
        //map.put("returnUrl", URLDecoder.decode(returnUrl, "UTF-8"));
        try {
            String decode = URLDecoder.decode(returnUrl, "UTF-8");
            map.put("returnUrl", decode);
        } catch (UnsupportedEncodingException e) {
            log.error("[支付订单] 解析返回地址错误, returnUrl={}", returnUrl);
            e.printStackTrace();
        }

        //3.生成JSAPI页面调用的支付参数并签名,返回给微信端让用户向微信支付系统发起支付和确认支付.
        return new ModelAndView("pay/create", map);
    }

    /**
     * Post方式接收 微信异步通知
     *
     * @param notifyData @RequestBody用于将前端json字符串转换成nofityData对象
     * @return 返回json字符串
     */
    @PostMapping("/pay/notify")
    public ModelAndView notify(@RequestBody String notifyData) {
        //根据微信支付系统的异步通知来处理我们的订单
        payService.notify(notifyData);

        //返回微信支付系统 支付成功
        return new ModelAndView("pay/success");
    }

}
