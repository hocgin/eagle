package in.hocg.eagle.modules.mms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.basic.security.SecurityContext;
import in.hocg.eagle.modules.mms.pojo.qo.notify.PublishPrivateLetterQo;
import in.hocg.eagle.modules.mms.pojo.qo.notify.SearchNotifyPagingQo;
import in.hocg.eagle.modules.mms.pojo.vo.notify.NotifyComplexVo;
import in.hocg.eagle.modules.mms.pojo.vo.notify.SummaryVo;
import in.hocg.eagle.modules.mms.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * [消息模块] 通知-接收人表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-03-04
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService service;

    @UseLogger("分页查询 - 我的消息列表")
    @PostMapping
    public Result<IPage<NotifyComplexVo>> _search(@Validated @RequestBody SearchNotifyPagingQo qo) {
        qo.setReceiverId(qo.getUserId());
        return Result.success(service.search(qo));
    }

    @UseLogger("查看 - 我的消息概要")
    @GetMapping("/summary")
    public Result<SummaryVo> summary() {
        final Long currentUserId = SecurityContext.getCurrentUserId();
        return Result.success(service.selectSummary(currentUserId));
    }

    @UseLogger("发布 - 私信")
    @PostMapping("/private-letter/publish")
    public Result<Void> publishPrivateLetter(@RequestBody PublishPrivateLetterQo qo) {
        qo.setActorId(qo.getUserId());
        service.publishPrivateLetter(qo);
        return Result.success();
    }
}

