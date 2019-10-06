package com.imooc.xsell.dataobject;

import com.imooc.xsell.enums.OrderStatusEnum;
import com.imooc.xsell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 亚林
 * date 19/10/6,0006 16:09
 */
@Data
@Entity
@DynamicUpdate
public class OrderMaster {
  @Id
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
  private Integer orderStatus = OrderStatusEnum.NEW.getCode();
  /** 支付状态 */
  private Integer payStatus = PayStatusEnum.WAIT.getCode();
  /** 创建时间 */
  private Date createTime;
  /** 更新时间 */
  private Date updateTime;
  
  
}
