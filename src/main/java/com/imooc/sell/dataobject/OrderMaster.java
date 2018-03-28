package com.imooc.sell.dataobject;

import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体
 *
 * @author 向亚林
 * 2018/3/26 15:31
 */
@Data
@Entity
@DynamicUpdate
public class OrderMaster {
    /**订单号*/
    @Id
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
    private BigDecimal	orderAmount;

    /**订单状态: 默认0新下单*/
    //@Value("#{ T( 0 )}")
    private Integer	orderStatus = OrderStatusEnum.NEW.getCode();

    /**支付状态: 默认0未付款*/
    //@Value("#{ 0 }")
    private Integer	payStatus = PayStatusEnum.WAIT.getCode();

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date	updateTime;


}
