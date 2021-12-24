package in.hocg.eagle.modules.wx.controller;


import in.hocg.boot.logging.autoconfiguration.core.UseLogger;
import in.hocg.eagle.basic.pojo.ro.Insert;
import in.hocg.eagle.basic.pojo.ro.Update;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.wx.pojo.qo.reply.WxReplyRulePageQo;
import in.hocg.eagle.modules.wx.pojo.qo.reply.WxReplyRuleSaveQo;
import in.hocg.eagle.modules.wx.service.WxMpReplyRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 微信自动回复配置表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-05-15
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/wx-mp/reply-rule")
public class WxMpReplyRuleController {
    private final WxMpReplyRuleService service;

    @UseLogger("新增 - 匹配回复规则")
    @PostMapping
    public Result<Void> insertOne(@Validated({Insert.class}) @RequestBody WxReplyRuleSaveQo qo) {
        qo.setId(null);
        service.insertOne(qo);
        return Result.success();
    }

    @UseLogger("更新 - 匹配回复规则")
    @PutMapping("/{id:\\d+}")
    public Result<Void> updateOne(@PathVariable("id") Long id,
                                  @Validated({Update.class}) @RequestBody WxReplyRuleSaveQo qo) {
        qo.setId(id);
        service.updateOne(qo);
        return Result.success();
    }

    @UseLogger("分页查询 - 匹配回复规则")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody WxReplyRulePageQo qo) {
        return Result.success(service.paging(qo));
    }


    @UseLogger("查看详情 - 匹配回复规则")
    @GetMapping("/{id:\\d+}")
    public Result selectOne(@PathVariable("id") Long id) {
        return Result.success(service.selectOne(id));
    }

}

