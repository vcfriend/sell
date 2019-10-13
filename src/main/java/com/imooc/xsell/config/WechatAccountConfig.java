package com.imooc.xsell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 亚林
 * date 19/10/12,0012 10:10
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
  
  /** 微信公众号AppId */
  private String mpAppId;
  
  /** 微信公众号AppSecret */
  private String mpAppSecret;

  /**
   * 商户号
   */
  private String mchId;

  /**
   * 商户密钥
   */
  private String mchKey;

  /**
   * 商户证书路径
   */
  private String keyPath;

  /**
   * 微信支付异步通知地址
   */
  private String notifyUrl;
  
}
