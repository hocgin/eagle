package in.hocg.eagle.modules.wx.controller;


import in.hocg.web.aspect.logger.UseLogger;
import in.hocg.web.pojo.qo.IdQo;
import in.hocg.web.result.Result;
import in.hocg.eagle.modules.wx.pojo.qo.material.*;
import in.hocg.eagle.modules.wx.service.WxMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [微信模块] 微信素材库 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-05-05
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/wx-mp/material")
public class WxMaterialController {
    private final WxMaterialService service;

    @GetMapping("/video/{appid}/{mediaId}")
    public ResponseEntity video(@PathVariable("appid") String appid, @PathVariable String mediaId) {
        return Result.stream(service.getVideoStream(appid, mediaId));
    }

    @GetMapping("/voice/{appid}/{mediaId}")
    public ResponseEntity voice(@PathVariable("appid") String appid, @PathVariable String mediaId) {
        return Result.stream(service.getVoiceStream(appid, mediaId));
    }

    @GetMapping("/image/{appid}/{mediaId}")
    public ResponseEntity image(@PathVariable("appid") String appid, @PathVariable String mediaId) {
        return Result.stream(service.getImageStream(appid, mediaId));
    }

    @UseLogger("上传音频 - 微信素材")
    @PostMapping("/upload/voice")
    public Result uploadVoice(@Validated @RequestBody WxMaterialUploadVoiceQo qo) {
        service.uploadVoice(qo);
        return Result.success();
    }

    @UseLogger("上传视频 - 微信素材")
    @PostMapping("/upload/video")
    public Result uploadVideo(@Validated @RequestBody WxMaterialUploadVideoQo qo) {
        service.uploadVideo(qo);
        return Result.success();
    }

    @UseLogger("上传图片 - 微信素材")
    @PostMapping("/upload/image")
    public Result uploadImage(@Validated @RequestBody WxMaterialUploadImageQo qo) {
        service.uploadImage(qo);
        return Result.success();
    }

    @UseLogger("上传图文(新增) - 微信素材")
    @PostMapping("/upload/news")
    public Result uploadNews(@Validated @RequestBody WxMaterialUploadNewsQo qo) {
        qo.validThrow();
        service.uploadNews(qo);
        return Result.success();
    }

    @UseLogger("上传图文(更新) - 微信素材")
    @PostMapping("/update/news/{id}")
    public Result updateNews(@PathVariable("id") Long id,
                             @Validated @RequestBody WxMaterialUpdateNewsQo qo) {
        qo.setId(id);
        service.updateNews(qo);
        return Result.success();
    }

    @UseLogger("分页查询 - 微信素材")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody WxMaterialPageQo qo) {
        return Result.success(service.paging(qo));
    }

    @UseLogger("详情 - 微信素材")
    @GetMapping("/{id}")
    public Result selectOne(@PathVariable Long id) {
        final IdQo qo = new IdQo();
        qo.setId(id);
        return Result.success(service.selectOne(qo));
    }
}

