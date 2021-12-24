package in.hocg.eagle.modules.wx.controller;


import in.hocg.boot.logging.autoconfiguration.core.UseLogger;
import in.hocg.eagle.basic.pojo.ro.Insert;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.wx.pojo.qo.message.template.WxMpMessageTemplateRefreshQo;
import in.hocg.eagle.modules.wx.pojo.qo.message.template.WxMpMessageTemplatePageQo;
import in.hocg.eagle.modules.wx.service.WxMpMessageTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [微信模块] 消息模版表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-05-16
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/wx-mp/message-template")
public class WxMpMessageTemplateController {
    private final WxMpMessageTemplateService service;

    @UseLogger("刷新 - 消息模版")
    @PostMapping("/refresh")
    public Result<Void> refresh(@Validated({Insert.class}) @RequestBody WxMpMessageTemplateRefreshQo qo) {
        service.refresh(qo);
        return Result.success();
    }

    @UseLogger("分页查询 - 消息模版")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody WxMpMessageTemplatePageQo qo) {
        return Result.success(service.paging(qo));
    }

    @UseLogger("查看详情 - 消息模版")
    @GetMapping("/{id:\\d+}")
    public Result selectOne(@PathVariable("id") Long id) {
        return Result.success(service.selectOne(id));
    }

}

