package com.imooc.xsell.controller;

import com.imooc.xsell.ResultEnum.ResultEnum;
import com.imooc.xsell.exception.SellException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * @author 亚林
 * date 19/10/12,0012 16:41
 */
@Slf4j
@Controller
@RequestMapping("/wechat")
@AllArgsConstructor(onConstructor = @__(@Autowired) )
public class WechatController {

  private final WxMpService wxMpService;

  @GetMapping("/authorize")
  public String authorize(@RequestParam("returnUrl") String returnUrl) {
    //1. 配置
    //2. 调用方法
    String url = "http://xsell.nat300.top/sell/wechat/userInfo";
    String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));

    return "redirect:" + redirectUrl;
  }

  @GetMapping("/userInfo")
  public String userInfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl) {
    WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
    try {
      wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
    } catch (WxErrorException e) {
      log.error("[微信网页授权] {}", e);
      throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
    }
    String openId = wxMpOAuth2AccessToken.getOpenId();
    return "redirect:" + returnUrl + "?openid=" + openId;
  }
  
}
