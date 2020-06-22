package in.hocg.eagle.modules.wx.controller;


import in.hocg.web.aspect.logger.UseLogger;
import in.hocg.web.pojo.qo.IdQo;
import in.hocg.web.result.Result;
import in.hocg.eagle.modules.wx.pojo.qo.shorturl.WxMpShortUrlInsertQo;
import in.hocg.eagle.modules.wx.pojo.qo.shorturl.WxMpShortUrlPageQo;
import in.hocg.eagle.modules.wx.service.WxMpShortUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [微信模块] 短链接表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-05-24
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/wx-mp/short-url")
public class WxMpShortUrlController {
    private final WxMpShortUrlService service;

    @UseLogger("新增 - 短链接")
    @PostMapping
    public Result<Void> insertOne(@Validated @RequestBody WxMpShortUrlInsertQo qo) {
        service.insertOne(qo);
        return Result.success();
    }

    @UseLogger("分页查询 - 短链接")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody WxMpShortUrlPageQo qo) {
        return Result.success(service.paging(qo));
    }

    @UseLogger("查看详情 - 短链接")
    @GetMapping("/{id:\\d+}")
    public Result selectOne(@PathVariable Long id) {
        final IdQo qo = new IdQo();
        qo.setId(id);
        return Result.success(service.selectOne(qo));
    }
}

