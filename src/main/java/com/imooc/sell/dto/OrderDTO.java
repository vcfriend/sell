package com.imooc.sell.dto;

import com.imooc.sell.dataobject.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单信息 DTO
 * 用于展示前端要显示的数据和接收一些参数对数据库实体进行修改
 * @author 向亚林
 * 2018/3/26 21:02
 */
@Data
public class OrderDTO {
    /**订单号*/
    private String orderId;

    /**买家名字*/
    private String	buyerName;

    /**买家手机号*/
    private String	buyerPhone;

    /**买家地址*/
    private String	buyerAddress;

    /**买家微信Openid*/
    private String	buyerOpenid;

    /**订单总金额*/
    private BigDecimal orderAmount;

    /**订单状态: 默认0新下单*/
    private Integer	orderStatus;

    /**支付状态: 默认0未付款*/
    private Integer	payStatus;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date	updateTime;

    /**订单详情列表*/
    List<OrderDetail> orderDetailList;
}
