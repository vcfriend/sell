package com.imooc.sell.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.dataobject.OrderDetail;
import lombok.Data;
import utils.serializer.Date2LongSerializer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单信息 DTO
 * 用于展示前端要显示的数据和接收一些参数对数据库实体进行修改
 * @author 向亚林
 * 2018/3/26 21:02
 */
@Data
//#json返回值属性中不包含null的属性值
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY) //过时的方法
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**更新时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /**
     * 订单详情列表
     *
     * 如果属性是Json返回值中的必填项,就需要在此初始化
     * */
    List<OrderDetail> orderDetailList = new ArrayList<>();
}
