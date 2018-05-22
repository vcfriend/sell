package com.imooc.sell.controller;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultTypeInfoEnum;
import com.imooc.sell.execption.SellExecption;
import com.imooc.sell.service.OrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家订单
 *
 * @author 向亚林
 * 2018/5/15 11:46
 */
@Log4j
@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     *
     * @param page 页码 从1开始
     * @param size 每页显示条数
     * @param map  前端页面显示
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findAll(pageRequest);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/list", map);
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("cancel")
    public ModelAndView cancel(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "size", defaultValue = "10") Integer size,
                               @RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        String url = "/seller/order/list?page=" + page + "&size=" + size;
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellExecption e) {
            log.info("[卖家端取消订单] 发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", url);
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultTypeInfoEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url", url);
        return new ModelAndView("common/success");
    }

    /**
     * 订单详情
     * page和size用于订单详情返回时,回到当初商品列表的页面
     *
     * @param page
     * @param size
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("detail")
    public ModelAndView detail(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "size", defaultValue = "10") Integer size,
                               @RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        String url = "/seller/order/list?page=" + page + "&size=" + size;
        //String url = "/seller/order/list";
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderService.findOne(orderId);
        } catch (SellExecption e) {
            log.info("[卖家端查询订单详情] 发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", url);
            return new ModelAndView("common/error", map);
        }
        map.put("orderDTO", orderDTO);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/detail");
    }

    /**
     * 完结订单
     * @param page
     * @param size
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("finish")
    public ModelAndView finish(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "size", defaultValue = "10") Integer size,
                               @RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        String url = "/seller/order/list?page=" + page + "&size=" + size;
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (SellExecption e) {
            log.info("[卖家端完结订单] 发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", url);
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultTypeInfoEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url", url);
        return new ModelAndView("common/success");
    }
}


