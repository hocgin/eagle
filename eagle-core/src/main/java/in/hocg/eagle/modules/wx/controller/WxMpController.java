package in.hocg.eagle.modules.wx.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Created by hocgin on 2020/4/21.
 * email: gin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/wx-mp")
public class WxMpController {
    private final WxMpService wxMpService;
    private final WxMpMessageRouter wxMpMessageRouter;


    @GetMapping(produces = "text/plain;charset=utf-8")
    public String connect(@RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            return "非法参数";
        }

        if (wxMpService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";
    }

    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String connect(@RequestBody String requestBody,
                          @RequestParam("signature") String signature,
                          @RequestParam("timestamp") String timestamp,
                          @RequestParam("nonce") String nonce,
                          @RequestParam("openid") String openid,
                          @RequestParam(name = "encrypt_type", required = false) String encType,
                          @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            return  "非法请求";
        }

        String result = null;

        // 如果使用明文方式
        if (Objects.isNull(encType)) {
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (Objects.isNull(outMessage)) {
                return "";
            }

            result = outMessage.toXml();
        }

        // 如果使用加密方式
        else if ("aes".equalsIgnoreCase(encType)) {
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxMpService.getWxMpConfigStorage(),
                timestamp, nonce, msgSignature);
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (Objects.isNull(outMessage)) {
                return "";
            }

            result = outMessage.toEncryptedXml(wxMpService.getWxMpConfigStorage());
        }
        return result;
    }

    private WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            return wxMpMessageRouter.route(message);
        } catch (Exception e) {
            log.error("微信消息路由错误", e);
        }
        return null;
    }
}
