package in.hocg.eagle.modules.wx.controller;

import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.wx.pojo.qo.menu.WxMenuInsertQo;
import in.hocg.eagle.modules.wx.pojo.qo.menu.WxMenuPagingQo;
import in.hocg.eagle.modules.wx.pojo.qo.menu.WxMenuUpdateQo;
import in.hocg.eagle.modules.wx.service.WxMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hocgin on 2020/4/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/wx/menu")
public class WxMenuController {
    private final WxMenuService service;

    @UseLogger("新增 - 微信菜单")
    @PostMapping
    public Result<Void> insertOne(@Validated @RequestBody WxMenuInsertQo qo) {
        service.insertOne(qo);
        return Result.success();
    }

    @UseLogger("更新 - 微信菜单")
    @PutMapping("/{id}")
    public Result<Void> updateOne(@PathVariable Long id,
                                  @Validated @RequestBody WxMenuUpdateQo qo) {
        qo.setId(id);
        service.updateOne(qo);
        return Result.success();
    }

    @UseLogger("分页查询 - 微信菜单")
    @PostMapping("/_paging")
    public Result paging(@Validated @RequestBody WxMenuPagingQo qo) {
        return Result.success(service.paging(qo));
    }

    @UseLogger("查看详情 - 微信菜单")
    @GetMapping("/{id}")
    public Result selectOne(@PathVariable Long id) {
        return Result.success(service.selectOne(id));
    }

    @UseLogger("同步到微信 - 微信菜单")
    @PostMapping("/sync/{id}")
    public Result sync(@PathVariable Long id) {
        service.sync(id);
        return Result.success();
    }

}
