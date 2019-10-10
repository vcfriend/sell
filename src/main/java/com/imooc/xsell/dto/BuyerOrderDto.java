package com.imooc.xsell.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.xsell.dataobject.OrderDetail;
import com.imooc.xsell.utils.serialize.Date2LongSerialize;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *  买家订单dto
 * @author 亚林
 * date 19/10/7,0007 10:02
 */
@Data
@Accessors(chain = true)
public class BuyerOrderDto {
  /** 订单id. */
  private String orderId;
  /** 买家名字 */
  private String buyerName;
  /** 买家手机号 */
  private String buyerPhone;
  /** 买家地址 */
  private String buyerAddress;
  /** 买家微信Openid */
  private String buyerOpenid;
  /**订单总金额*/
  private BigDecimal orderAmount;
  /** 订单状态 默认为0 新下单 */
  private Integer orderStatus;
  /** 支付状态 默认为0 未支付*/
  private Integer payStatus;
  /** 创建时间 使用JsonSerialize注解自动对返回的时间戳按秒显示*/
  @JsonSerialize(using = Date2LongSerialize.class)
  private Date createTime;
  /** 更新时间 */
  @JsonSerialize(using = Date2LongSerialize.class)
  private Date updateTime;
  /** 买家订单详情列表 */
  List<OrderDetail> orderDetailList;
}
