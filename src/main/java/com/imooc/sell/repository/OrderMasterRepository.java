package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单 Repository
 *
 * @author 向亚林
 * 2018/3/26 15:53
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
    /**
     * 根据买家ID分页查询订单
     * @param buyerOpenid 买家微信ID
     * @param pageable 分页参数
     * @return 返回分页信息
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

}
