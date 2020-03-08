package in.hocg.eagle.modules.notify.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.IntEnum;
import in.hocg.eagle.basic.constant.datadict.NotifyType;
import in.hocg.eagle.basic.constant.datadict.SubjectType;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.mapstruct.NotificationMapping;
import in.hocg.eagle.mapstruct.dto.PublishNotifyDto;
import in.hocg.eagle.mapstruct.qo.notify.PublishPrivateLetterQo;
import in.hocg.eagle.mapstruct.qo.notify.PublishSubscriptionDto;
import in.hocg.eagle.mapstruct.qo.notify.SearchNotifyPageQo;
import in.hocg.eagle.mapstruct.vo.account.AccountComplexVo;
import in.hocg.eagle.mapstruct.vo.notify.NotifyItemVo;
import in.hocg.eagle.mapstruct.vo.notify.SummaryVo;
import in.hocg.eagle.modules.account.service.AccountService;
import in.hocg.eagle.modules.notify.entity.Notification;
import in.hocg.eagle.modules.notify.entity.Notify;
import in.hocg.eagle.modules.notify.entity.Subscription;
import in.hocg.eagle.modules.notify.mapper.NotificationMapper;
import in.hocg.eagle.modules.notify.service.NotificationService;
import in.hocg.eagle.modules.notify.service.NotifyService;
import in.hocg.eagle.modules.notify.service.SubscriptionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
    private final SubscriptionService subscriptionService;
    private final NotificationMapping mapping;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<NotifyItemVo> search(SearchNotifyPageQo qo) {
        final IPage<Notification> result = baseMapper.search(qo, qo.page());
        result.getRecords().forEach(notification -> {
            final Long notifyId = notification.getNotifyId();
            final Long receiverId = notification.getReceiverId();
            updateReadyAtNow(notifyId, receiverId);
        });
        return result.convert(this::convertNotifyItem);
    }

    private NotifyItemVo convertNotifyItem(Notification notification) {
        final Long notifyId = notification.getNotifyId();
        final Long receiverId = notification.getReceiverId();
        final Notify notify = notifyService.getById(notifyId);
        final Long actorId = notify.getActorId();
        final AccountComplexVo receiver = accountService.selectOneComplex(receiverId);
        final AccountComplexVo actor = accountService.selectOneComplex(actorId);
        return mapping.asSearchNotifyVo(notification, notify, receiver, actor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOne(Notification entity) {
        verifyEntity(entity);
        baseMapper.insert(entity);
    }

    @Override
    @ApiOperation("发布私信")
    @Transactional(rollbackFor = Exception.class)
    public void publishPrivateLetter(PublishPrivateLetterQo qo) {
        final PublishNotifyDto dto = new PublishNotifyDto();
        dto.setNotifyType(NotifyType.PrivateLetter);
        dto.setActorId(qo.getActorId());
        dto.setContent(qo.getContent());
        dto.setReceivers(qo.getReceivers());
        notifyService.published(dto);
    }

    @Override
    @ApiOperation("发布订阅通知")
    @Transactional(rollbackFor = Exception.class)
    public void publishSubscription(PublishSubscriptionDto subscriptionDto) throws Throwable {
        final PublishNotifyDto dto = new PublishNotifyDto();
        final Long subjectId = subscriptionDto.getSubjectId();
        final Integer notifyTypeCode = subscriptionDto.getNotifyType();
        final NotifyType notifyType = IntEnum.of(notifyTypeCode, NotifyType.class)
            .orElseThrow((Supplier<Throwable>) () -> ServiceException.wrap("通知类型错误"));
        final Integer subjectTypeCode = subscriptionDto.getSubjectType();
        final SubjectType subjectType = IntEnum.of(subjectTypeCode, SubjectType.class)
            .orElseThrow((Supplier<Throwable>) () -> ServiceException.wrap("订阅类型错误"));
        dto.setNotifyType(notifyType);
        dto.setSubjectType(subjectType);
        dto.setSubjectId(subjectId);
        final List<Subscription> subscriptions = subscriptionService.selectListBySubjectIdAndSubjectTypeAndNotifyType(subjectId, subjectTypeCode, notifyTypeCode);
        dto.setActorId(subscriptionDto.getActorId());
        if (subscriptions.isEmpty()) {
            return;
        }
        final List<Long> receivers = subscriptions.stream()
            .map(Subscription::getSubscriberId)
            .collect(Collectors.toList());
        dto.setReceivers(receivers);
        notifyService.published(dto);
    }

    @Override
    @ApiOperation("查询详情")
    @Transactional(rollbackFor = Exception.class)
    public SummaryVo selectSummary(Long accountId) {
        Integer readyCount = countByReady(accountId);
        Integer unReadyCount = countByUnReady(accountId);
        Integer top5 = 5;
        final List<NotifyItemVo> privateLetter = baseMapper.selectListByReceiverIdAndNotifyType(accountId, NotifyType.PrivateLetter.getCode(), top5)
            .stream().map(this::convertNotifyItem).collect(Collectors.toList());
        final List<NotifyItemVo> subscription = baseMapper.selectListByReceiverIdAndNotifyType(accountId, NotifyType.Announcement.getCode(), top5)
            .stream().map(this::convertNotifyItem).collect(Collectors.toList());
        final List<NotifyItemVo> announcement = baseMapper.selectListByReceiverIdAndNotifyType(accountId, NotifyType.Announcement.getCode(), top5)
            .stream().map(this::convertNotifyItem).collect(Collectors.toList());

        return new SummaryVo()
            .setReady(readyCount)
            .setUnready(unReadyCount)
            .setAnnouncement(announcement)
            .setSubscription(subscription)
            .setPrivateLetter(privateLetter);
    }

    private Integer countByUnReady(Long accountId) {
        return lambdaQuery()
            .eq(Notification::getReceiverId, accountId)
            .isNull(Notification::getReadAt).count();
    }

    private Integer countByReady(Long accountId) {
        return lambdaQuery()
            .eq(Notification::getReceiverId, accountId)
            .isNotNull(Notification::getReadAt).count();
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateReadyAtNow(Long notifyId, Long receiverId) {
        baseMapper.updateReadyAtNowByNotifyId(notifyId, receiverId);
    }

    @Override
    public void verifyEntity(Notification entity) {

    }
}
