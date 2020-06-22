package in.hocg.eagle.modules.wx.controller;


import in.hocg.web.aspect.logger.UseLogger;
import in.hocg.web.pojo.qo.IdQo;
import in.hocg.web.result.Result;
import in.hocg.eagle.modules.wx.pojo.qo.qrcode.WxMpQrcodeInsertQo;
import in.hocg.eagle.modules.wx.pojo.qo.qrcode.WxMpQrcodePageQo;
import in.hocg.eagle.modules.wx.service.WxMpQrcodeService;
import in.hocg.web.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [微信模块] 二维码表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-05-24
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/wx-mp/qrcode")
public class WxMpQrcodeController {
    private final WxMpQrcodeService service;

    @UseLogger("新增 - 二维码")
    @PostMapping
    public Result<Void> insertOne(@Validated @RequestBody WxMpQrcodeInsertQo qo) {
        if (qo.getUseExpireQrCode()) {
            ValidUtils.notNull(qo.getExpireSeconds(), "过期时间错误");
        } else {
            qo.setExpireSeconds(-1);
        }
        if (qo.getUseSceneId()) {
            ValidUtils.notNull(qo.getSceneId(), "场景值错误");
            qo.setSceneStr(null);
        } else {
            ValidUtils.notNull(qo.getSceneStr(), "场景值错误");
            qo.setSceneId(null);
        }

        service.insertOne(qo);
        return Result.success();
    }

    @UseLogger("分页查询 - 二维码")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody WxMpQrcodePageQo qo) {
        return Result.success(service.paging(qo));
    }

    @UseLogger("查看详情 - 二维码")
    @GetMapping("/{id:\\d+}")
    public Result selectOne(@PathVariable Long id) {
        final IdQo qo = new IdQo();
        qo.setId(id);
        return Result.success(service.selectOne(qo));
    }
}

