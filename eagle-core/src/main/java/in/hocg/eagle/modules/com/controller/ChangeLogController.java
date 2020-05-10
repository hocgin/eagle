package in.hocg.eagle.modules.com.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.com.pojo.qo.changelog.ChangeLogPagingQo;
import in.hocg.eagle.modules.com.pojo.vo.changelog.ChangeLogComplexVo;
import in.hocg.eagle.modules.com.service.ChangeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * [基础模块] 业务操作日志表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-04-11
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/change-log")
public class ChangeLogController {
    private final ChangeLogService service;

    @PostMapping({"/_paging"})
    @UseLogger("分页查询 - 业务变更日志")
    public Result<IPage<ChangeLogComplexVo>> paging(@Validated @RequestBody ChangeLogPagingQo qo) {
        return Result.success(service.pagingWithComplex(qo));
    }

}

