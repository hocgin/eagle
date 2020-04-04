package in.hocg.eagle.modules.com.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.com.pojo.qo.requestlog.RequestLogPagingQo;
import in.hocg.eagle.modules.com.pojo.vo.requestlog.RequestLogComplexVo;
import in.hocg.eagle.modules.com.service.RequestLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hocgin on 2020/4/4.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/request-log")
public class RequestLogController {
    private final RequestLogService service;

    @PostMapping({"/_paging"})
    @UseLogger("分页查询 - 请求日志")
    public Result<IPage<RequestLogComplexVo>> paging(@Validated @RequestBody RequestLogPagingQo qo) {
        return Result.success(service.pagingWithComplex(qo));
    }
}
