package com.imooc.xsell.repository;

import com.imooc.xsell.dataobject.OrderDetail;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 订单详情
 *
 * @author 亚林
 * date 19/10/6,0006 17:13
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
  /**
   * @param orderId 订单id
   * @return 订单详情
   */
  OrderDetail findByOrOrderId(String orderId);

  /**
   * @param orderIdList 订单Id列表
   * @return 订单详情列表
   */
  List<OrderDetail> findByOrOrderIdIn(List<String> orderIdList);
}
