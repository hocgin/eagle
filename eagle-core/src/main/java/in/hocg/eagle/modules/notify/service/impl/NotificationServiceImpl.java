package in.hocg.eagle.modules.notify.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.mapstruct.NotificationMapping;
import in.hocg.eagle.mapstruct.qo.notify.SearchNotifyPageQo;
import in.hocg.eagle.mapstruct.vo.account.IdAccountComplexVo;
import in.hocg.eagle.mapstruct.vo.notify.SearchNotifyVo;
import in.hocg.eagle.modules.account.service.AccountService;
import in.hocg.eagle.modules.notify.entity.Notification;
import in.hocg.eagle.modules.notify.entity.Notify;
import in.hocg.eagle.modules.notify.mapper.NotificationMapper;
import in.hocg.eagle.modules.notify.service.NotificationService;
import in.hocg.eagle.modules.notify.service.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * <p>
 * [消息模块] 通知-接收人表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-04
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class NotificationServiceImpl extends AbstractServiceImpl<NotificationMapper, Notification> implements NotificationService {

    private final AccountService accountService;
    private final NotifyService notifyService;
    private final NotificationMapping mapping;

    @Override
    public IPage<SearchNotifyVo> search(SearchNotifyPageQo qo) {
        final IPage<Notification> result = baseMapper.search(qo, qo.page());
        return result.convert(notification -> {
            final Long notifyId = notification.getNotifyId();
            final Long receiverId = notification.getReceiverId();
            final Notify notify = notifyService.getById(notifyId);
            final Long actorId = notify.getActorId();
            final IdAccountComplexVo receiver = accountService.selectOneComplex(receiverId);
            final IdAccountComplexVo actor = accountService.selectOneComplex(actorId);
            return mapping.asSearchNotifyVo(notification, notify, receiver, actor);
        });
    }
}
