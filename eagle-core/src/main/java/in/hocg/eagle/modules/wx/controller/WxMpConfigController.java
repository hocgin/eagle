package in.hocg.eagle.modules.wx.controller;


import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.pojo.qo.Insert;
import in.hocg.eagle.basic.pojo.qo.Update;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.wx.pojo.qo.WxMpConfigPagingQo;
import in.hocg.eagle.modules.wx.pojo.qo.WxMpConfigSaveQo;
import in.hocg.eagle.modules.wx.service.WxMpConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 微信公众号配置表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-04-25
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/wx-mp/config")
public class WxMpConfigController {
    private final WxMpConfigService service;

    @UseLogger("新增 - 微信公众号配置")
    @PostMapping
    public Result<Void> insertOne(@Validated({Insert.class}) @RequestBody WxMpConfigSaveQo qo) {
        service.saveOne(qo);
        return Result.success();
    }

    @UseLogger("更新 - 微信公众号配置")
    @PutMapping("/{appid}")
    public Result<Void> updateOne(@PathVariable("appid") String appid,
                                  @Validated({Update.class}) @RequestBody WxMpConfigSaveQo qo) {
        qo.setAppid(appid);
        service.saveOne(qo);
        return Result.success();
    }

    @UseLogger("分页查询 - 微信公众号配置")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody WxMpConfigPagingQo qo) {
        return Result.success(service.paging(qo));
    }

    @UseLogger("查询所有 - 微信公众号配置")
    @GetMapping("/all")
    public Result all() {
        final WxMpConfigPagingQo qo = new WxMpConfigPagingQo();
        qo.setSize(1);
        qo.setSize(Integer.MAX_VALUE);
        return Result.success(service.paging(qo).getRecords());
    }

    @UseLogger("查看详情 - 微信公众号配置")
    @GetMapping("/{appid}")
    public Result selectOne(@PathVariable("appid") String appid) {
        return Result.success(service.selectOne(appid));
    }

}

