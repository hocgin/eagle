package in.hocg.eagle.modules.com.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.boot.logging.autoconfiguration.core.UseLogger;
import in.hocg.eagle.basic.pojo.ro.Insert;
import in.hocg.eagle.basic.pojo.ro.Update;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.com.pojo.qo.systemsettings.SystemSettingsPagingQo;
import in.hocg.eagle.modules.com.pojo.qo.systemsettings.SystemSettingsSaveQo;
import in.hocg.eagle.modules.com.pojo.vo.systemsettings.SystemSettingsComplexVo;
import in.hocg.eagle.modules.com.service.SystemSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [基础模块] 系统配置表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/system-settings")
public class SystemSettingsController {
    private final SystemSettingsService service;

    @PostMapping({"/_paging"})
    @UseLogger("分页查询 - 系统配置")
    public Result<IPage<SystemSettingsComplexVo>> paging(@Validated @RequestBody SystemSettingsPagingQo qo) {
        return Result.success(service.paging(qo));
    }

    @GetMapping("/{id:\\d+}:complex")
    @UseLogger("详情 - 系统配置")
    public Result<SystemSettingsComplexVo> selectOne(@PathVariable Long id) {
        return Result.success(service.selectOne(id));
    }

    @PostMapping
    @UseLogger("新增 - 系统配置")
    public Result insertOne(@Validated({Insert.class}) @RequestBody SystemSettingsSaveQo qo) {
        qo.setId(null);
        service.saveOne(qo);
        return Result.success();
    }

    @PutMapping("/{id:\\d+}")
    @UseLogger("修改 - 系统配置")
    public Result updateOne(@PathVariable Long id, @Validated({Update.class}) @RequestBody SystemSettingsSaveQo qo) {
        qo.setId(id);
        service.saveOne(qo);
        return Result.success();
    }
}

