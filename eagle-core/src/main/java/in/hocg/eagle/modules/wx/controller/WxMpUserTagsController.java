package in.hocg.eagle.modules.wx.controller;


import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.wx.pojo.qo.user.tags.WxMpSetUserTagsQo;
import in.hocg.eagle.modules.wx.pojo.qo.user.tags.WxMpUnsetUserTagsQo;
import in.hocg.eagle.modules.wx.pojo.qo.user.tags.WxMpUserTagsPageQo;
import in.hocg.eagle.modules.wx.pojo.qo.user.tags.WxMpUserTagsRefreshQo;
import in.hocg.eagle.modules.wx.service.WxMpUserTagsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [微信模块] 用户标签表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-05-19
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/wx-mp/tags")
public class WxMpUserTagsController {
    private final WxMpUserTagsService service;

    @UseLogger("刷新 - 微信用户标签")
    @PostMapping("/refresh")
    public Result refresh(@Validated @RequestBody WxMpUserTagsRefreshQo qo) {
        service.refresh(qo);
        return Result.success();
    }

    @UseLogger("分页查询 - 微信用户标签")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody WxMpUserTagsPageQo qo) {
        return Result.success(service.paging(qo));
    }

    @UseLogger("查看详情 - 微信用户标签")
    @GetMapping("/{id:\\d+}")
    public Result selectOne(@PathVariable("id") Long id) {
        return Result.success(service.selectOne(id));
    }

    @UseLogger("给用户设置标签 - 微信用户标签")
    @PostMapping("/{tagsId:\\d+}/set")
    public Result<Void> setTagWithinUser(@PathVariable("tagsId") Long tagsId,
                                         @RequestBody WxMpSetUserTagsQo qo) {
        qo.setTagsId(tagsId);
        service.setTagWithinUser(qo);
        return Result.success();
    }

    @UseLogger("给用户移除标签 - 微信用户标签")
    @DeleteMapping("/{tagsId:\\d+}/unset")
    public Result<Void> unsetTagWithinUser(@PathVariable("tagsId") Long tagsId,
                                           @RequestBody WxMpUnsetUserTagsQo qo) {
        qo.setTagsId(tagsId);
        service.unsetTagWithinUser(qo);
        return Result.success();
    }

    @UseLogger("删除标签 - 微信用户标签")
    @DeleteMapping("/{id:\\d+}")
    public Result<Void> deleteOne(@PathVariable("id") Long id) {
        service.deleteOne(id);
        return Result.success();
    }

}

