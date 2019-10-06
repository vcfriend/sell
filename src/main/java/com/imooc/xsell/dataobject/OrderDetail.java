package com.imooc.xsell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 订单详情
 *
 * @author 亚林
 * date 19/10/6,0006 16:46
 */
@Data
@Entity
public class OrderDetail {
  @Id
  private String detailId;
  /** 订单id */
  private String orderId;
  /** 商品id */
  private String productId;
  /** 商品名称 */
  private String productName;
  /** 商品单价 */
  private BigDecimal productPrice;
  /** 商品数量 */
  private Integer productQuantity;
  /** 商品小图 */
  private String productIcon;

}
