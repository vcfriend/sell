package com.imooc.xsell.utils;

import lombok.Data;

import java.util.Random;

/**
 * @author 亚林
 * date 19/10/7,0007 10:31
 */
@Data
public class KeyUtil {
  /**
   * @return 生成唯一主键 格式: 时间+随机数
   */
  public static synchronized String genUniqueKey() {
    Random random = new Random();
    int number = random.nextInt(900000) + 100000;
    return System.currentTimeMillis() + String.valueOf(number);
  }
  
}
