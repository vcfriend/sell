package com.imooc.xsell.repository;

import com.imooc.xsell.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

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
  List<OrderDetail> findByOrderId(String orderId);
  
}
