package com.imooc.sell.controller;

import com.imooc.sell.enums.ResultTypeInfoEnum;
import com.imooc.sell.execption.SellExecption;
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
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

/**
 * @author 向亚林
 * 2018/4/13 21:04
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    /**
     * 构造网页授权Url让用户来点击确认,为了获取openid
     * @param returnUrl 回调地址
     * @return 重定向地址
     */
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        //1.配置
        //2.调用
        String url = "http://xsell.natapp1.cc/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));
        //重定向地址
        return "redirect:" + redirectUrl;
    }

    /**
     * 微信端用户同意授权后获取 AccessToken,通过AccessToken获取openid
     * @param code 微信端用户同意授权后回调后,获取到的Authorization code
     * @param returnUrl 回调地址
     * @return 返回回调地址并携带openid
     */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();

        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("[微信网页授权] {}",e);
            throw new SellExecption(ResultTypeInfoEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:" + returnUrl + "?openid=" + openId;
    }
}
