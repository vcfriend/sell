package com.imooc.xsell.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 亚林
 * @time 19/10/4,0004、16:22
 */
@Data
@Entity
@DynamicUpdate //自动更新时间
@Accessors(chain = true)
public class ProductCategory {
  /**
   * 类目id
   */
  @Id
  @GeneratedValue
  private Integer categoryId;
  /**
   * 类目名字
   */
  private String categoryName;
  /**
   * 类目编号
   */
  private Integer categoryType;

  private Date createTime;

  private Date updateTime;
}
