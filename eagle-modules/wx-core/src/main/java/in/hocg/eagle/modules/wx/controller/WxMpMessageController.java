package in.hocg.eagle.modules.wx.controller;

import in.hocg.web.aspect.logger.UseLogger;
import in.hocg.web.result.Result;
import in.hocg.eagle.modules.wx.pojo.qo.message.SendMessageToGroupQo;
import in.hocg.eagle.modules.wx.pojo.qo.message.SendMessageToUserQo;
import in.hocg.eagle.modules.wx.pojo.qo.message.SendPreviewMessageToUserQo;
import in.hocg.eagle.modules.wx.pojo.qo.message.SendTemplateMessageToUserQo;
import in.hocg.eagle.modules.wx.service.WxMpMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hocgin on 2020/5/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/wx-mp/message")
public class WxMpMessageController {
    private final WxMpMessageService service;

    @UseLogger("发送消息 - 单发")
    @PostMapping("/send@user")
    public Result<Void> sendMessageToUser(@Validated @RequestBody SendMessageToUserQo qo) {
        service.sendMessageToUser(qo);
        return Result.success();
    }

    @UseLogger("发送消息 - 群发")
    @PostMapping("/send@group")
    public Result<Void> sendMessageToGroup(@Validated @RequestBody SendMessageToGroupQo qo) {
        service.sendMessageToGroup(qo);
        return Result.success();
    }

    @UseLogger("发送模版消息 - 单发")
    @PostMapping("/template/send@user")
    public Result<Void> sendTemplateMessageToUser(@Validated @RequestBody SendTemplateMessageToUserQo qo) {
        service.sendTemplateMessageToUser(qo);
        return Result.success();
    }

    @UseLogger("发送预览消息 - 单发")
    @PostMapping("/preview/send@user")
    public Result<Void> sendPreviewMessageToUser(@Validated @RequestBody SendPreviewMessageToUserQo qo) {
        service.sendPreviewMessageToUser(qo);
        return Result.success();
    }


}
