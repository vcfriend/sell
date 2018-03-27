package com.imooc.sell.service.impl;

import com.imooc.sell.converter.OrderMaster2OrderDTOConverter;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.ResultTypeInfoEnum;
import com.imooc.sell.execption.SellExecption;
import com.imooc.sell.repository.OrderDetailRepository;
import com.imooc.sell.repository.OrderMasterRepository;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import utils.KeyUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单 service
 * @author 向亚林
 * 2018/3/26 21:15
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    /**
     * 创建订单
     * 由订单传输对象 OrderDTO 中的商品ID去完成数据库中查询并完善 OrderDTO 中的其他属性
     * 再由OrderDTO中的属性去完善订单信息OrderMaster
     *
     * @param orderDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO create(OrderDTO orderDTO) {

        //生成订单Id
        String orderId = KeyUtil.generateUniqueKey();
        //订单总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //订单详细列表
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        //1. 查询商品(数量,价格)
        for (OrderDetail orderDetail : orderDetailList) {
            ProductInfo one = productInfoService.findOne(orderDetail.getProductId());
            if (null == one) {
                throw new SellExecption(ResultTypeInfoEnum.PRODUCT_NOT_EXIST);
            }
            //2. 计算订单总价
            orderAmount = one.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库
            orderDetail.setDetailId(KeyUtil.generateUniqueKey());
            orderDetail.setOrderId(orderId);
            //由数据库中查询的商品信息去完善订单详细列表中的商品信息
            BeanUtils.copyProperties(one, orderDetail);
            orderDetailRepository.save(orderDetail);

        }
        //完善orderDTO中的
        orderDTO.setOrderId(orderId);
        orderDTO.setOrderAmount(orderAmount);
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderDTO.setCreateTime(new Date());

        //3. 写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        //由orderDTO实体去完善orderMaster实体中的订单信息
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMasterRepository.save(orderMaster);

        //4. 扣库存
        //由订单详情信息去构造财物车 CartDTO 实体信息
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        //由商品 service 去扣减库存
        productInfoService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    /**
     * 查询单个订单
     *
     * @param orderId
     */
    @Override
    public OrderDTO findOne(String orderId) {
        //根据订单Id查询出订单
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            throw new SellExecption(ResultTypeInfoEnum.ORDER_NOT_EXIST);
        }
        //根据订单Id查询出订单详情
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellExecption(ResultTypeInfoEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    /**
     * 根据买家Id分页查询订单列表
     *
     * @param bueryOpenid
     * @param pageable
     */
    @Override
    public Page<OrderDTO> findAllByOpentid(String bueryOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(bueryOpenid, pageable);

        //从List<OrderMaster>转换到List<OrderDTO>
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        PageImpl<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    /**
     * 取消订单
     *
     * @param orderDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态是否是新订单
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[取消订单] 订单状态不正确,orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderId());
            throw new SellExecption(ResultTypeInfoEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态: 为取消状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster save = orderMasterRepository.save(orderMaster);
        if (save == null) {
            log.error("[取消订单] 更新失败, orderMaster={}", orderMaster);
            throw new SellExecption(ResultTypeInfoEnum.ORDER_UPDATE_FLASE);
        }
        //返回库存 -加库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[取消订单] 订单中无商品详情, orderDTO={}", orderDTO);
            throw new SellExecption(ResultTypeInfoEnum.ORDER_DETAIL_EMPTY);
        }
        //由订单构建出购物项
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        //取消购物项, 增加库存
        productInfoService.increaseStock(cartDTOList);

        //如果已支付, 需要退款
        if (orderDTO.getOrderStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //TODO
        }
        return orderDTO;
    }

    /**
     * 完结订单
     *
     * @param orderDTO
     */
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    /**
     * 支付订单
     *
     * @param orderDTO
     */
    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
