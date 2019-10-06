package com.imooc.xsell.repository;

import com.imooc.xsell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 买家订单信息
 * @author 亚林
 * date 19/10/6,0006 17:07
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
  /**
   * 根据买家微信openid查询
   * @param buyerOpenid 买家微信openid
   * @param pageable 分页信息
   * @return 买家订单分页信息
   */
  Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
