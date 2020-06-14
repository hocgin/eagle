package in.hocg.eagle.modules.mms.service.impl;

import in.hocg.web.AbstractServiceImpl;
import in.hocg.web.constant.datadict.NotifyType;
import in.hocg.web.constant.datadict.SubjectType;
import in.hocg.eagle.modules.mms.mapstruct.NotifyMapping;
import in.hocg.eagle.modules.mms.entity.Notification;
import in.hocg.eagle.modules.mms.entity.Notify;
import in.hocg.eagle.modules.mms.mapper.NotifyMapper;
import in.hocg.eagle.modules.mms.pojo.dto.notify.PublishNotifyDto;
import in.hocg.eagle.modules.mms.service.NotificationService;
import in.hocg.eagle.modules.mms.service.NotifyService;
import in.hocg.web.utils.LangUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * <p>
 * [消息模块] 通知表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-04
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class NotifyServiceImpl extends AbstractServiceImpl<NotifyMapper, Notify> implements NotifyService {

    private final NotifyMapping mapping;
    private final NotificationService notificationService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void published(PublishNotifyDto dto) {
        final Notify entity = mapping.asNotify(dto);
        final Optional<Integer> subjectType = LangUtils.callIfNotNull(dto.getSubjectType(), SubjectType::getCode);
        entity.setSubjectType(subjectType.orElse(null));
        final Optional<Integer> notifyType = LangUtils.callIfNotNull(dto.getNotifyType(), NotifyType::getCode);
        entity.setNotifyType(notifyType.orElse(null));
        entity.setCreator(dto.getUserId());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setContent(dto.getNotifyType().getMessage());
        validInsert(entity);

        Notification notification;
        for (Long receiverId : dto.getReceivers()) {
            notification = new Notification();
            notification.setNotifyId(entity.getId());
            notification.setReceiverId(receiverId);

            notificationService.validInsert(notification);
        }
    }

    @Override
    public void validEntity(Notify entity) {

    }
}
