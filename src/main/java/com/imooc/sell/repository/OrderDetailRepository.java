package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 订单详情 Repository
 * @author 向亚林
 * 2018/3/26 17:26
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    /**
     * 根据订单Id查找订单详情
     * @param orderId 订单Id
     * @return 返回订单详情
     */
    List<OrderDetail> findByOrderId(String orderId);
}
