package com.imooc.xsell.config;

import lombok.AllArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 使用微信公众号支付SDK的配置类
 *
 * @author 亚林
 * date 19/10/12,0012 17:46
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WechatMpConfig {

  private final WechatAccountConfig accountConfig;
  
  @Bean
  public WxMpService wxMpService() {
    WxMpServiceImpl wxMpService = new WxMpServiceImpl();
    WxMpInMemoryConfigStorage mp = new WxMpInMemoryConfigStorage();
    mp.setAppId(accountConfig.getMpAppId());
    mp.setSecret(accountConfig.getMpAppSecret());
    wxMpService.setWxMpConfigStorage(mp);
    return wxMpService;
  }
}
