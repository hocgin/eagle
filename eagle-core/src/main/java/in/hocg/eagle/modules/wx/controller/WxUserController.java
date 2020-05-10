package in.hocg.eagle.modules.wx.controller;


import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.wx.pojo.qo.user.WxMpUserPagingQo;
import in.hocg.eagle.modules.wx.service.WxUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 微信用户表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-04-25
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/wx-mp/user")
public class WxUserController {
    private final WxUserService service;

    @UseLogger("分页查询 - 微信用户")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody WxMpUserPagingQo qo) {
        return Result.success(service.paging(qo));
    }

    @UseLogger("查看详情 - 微信用户")
    @GetMapping("/{id:\\d+}")
    public Result updateOne(@PathVariable Long id) {
        final IdQo qo = new IdQo();
        qo.setId(id);
        return Result.success(service.selectOne(qo));
    }

}

