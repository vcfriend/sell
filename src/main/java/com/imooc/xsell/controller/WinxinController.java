package com.imooc.xsell.controller;

import com.imooc.xsell.config.WechatAccountConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author 亚林
 * date 19/10/11,0011 20:10
 */
@Slf4j
@RestController
@RequestMapping("/weixin")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WinxinController {

  private final WechatAccountConfig wechatAccountConfig;
  
  @GetMapping("/auth")
  public void auth(@RequestParam("code") String code) {
    log.info("进入auth方法");
    log.info("code={} ", code);
    // 第一步: 设置域名微信白名单
    // 第二步: 用户同意授权，获取code
    // 第三步: 通过code换取网页授权access_token
    String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wechatAccountConfig.getMpAppId() +
            "&secret=" + wechatAccountConfig.getMpAppSecret() +
            "&code=" + code + "&grant_type=authorization_code";
    String forObject = new RestTemplate().getForObject(url, String.class);
    System.out.println("forObject = " + forObject);

  }
}
