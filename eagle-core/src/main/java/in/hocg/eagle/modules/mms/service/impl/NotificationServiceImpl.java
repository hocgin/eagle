package in.hocg.eagle.modules.mms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.CodeEnum;
import in.hocg.eagle.basic.constant.datadict.NotifyType;
import in.hocg.eagle.basic.constant.datadict.SubjectType;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.modules.mms.mapstruct.NotificationMapping;
import in.hocg.eagle.modules.mms.entity.Notification;
import in.hocg.eagle.modules.mms.entity.Notify;
import in.hocg.eagle.modules.mms.mapper.NotificationMapper;
import in.hocg.eagle.modules.mms.pojo.dto.notify.PublishNotifyDto;
import in.hocg.eagle.modules.mms.pojo.qo.notify.PublishPrivateLetterQo;
import in.hocg.eagle.modules.mms.pojo.qo.notify.PublishSubscriptionDto;
import in.hocg.eagle.modules.mms.pojo.qo.notify.SearchNotifyPagingQo;
import in.hocg.eagle.modules.mms.pojo.vo.notify.NotifyComplexVo;
import in.hocg.eagle.modules.mms.pojo.vo.notify.SummaryVo;
import in.hocg.eagle.modules.mms.service.NotificationService;
import in.hocg.eagle.modules.mms.service.NotifyService;
import in.hocg.eagle.modules.mms.service.SubscriptionService;
import in.hocg.eagle.modules.ums.pojo.vo.account.AccountComplexVo;
import in.hocg.eagle.modules.ums.service.AccountService;
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
public class NotificationServiceImpl extends AbstractServiceImpl<NotificationMapper, Notification>
    implements NotificationService {

    private final AccountService accountService;
    private final NotifyService notifyService;
    private final SubscriptionService subscriptionService;
    private final NotificationMapping mapping;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<NotifyComplexVo> search(SearchNotifyPagingQo qo) {
        final IPage<Notification> result = baseMapper.search(qo, qo.page());
        result.getRecords().forEach(notification -> updateReadyAtNow(notification.getNotifyId(), notification.getReceiverId()));
        return result.convert(this::convertComplex);
    }

    private NotifyComplexVo convertComplex(Notification notification) {
        final Long notifyId = notification.getNotifyId();
        final Long receiverId = notification.getReceiverId();
        final Notify notify = notifyService.getById(notifyId);
        final Long actorId = notify.getActorId();
        final AccountComplexVo receiver = accountService.selectOneComplex(receiverId);
        final AccountComplexVo actor = accountService.selectOneComplex(actorId);
        return mapping.asSearchNotifyVo(notification, notify, receiver, actor);
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
        final NotifyType notifyType = CodeEnum.of(notifyTypeCode, NotifyType.class)
            .orElseThrow((Supplier<Throwable>) () -> ServiceException.wrap("通知类型错误"));
        final Integer subjectTypeCode = subscriptionDto.getSubjectType();
        final SubjectType subjectType = CodeEnum.of(subjectTypeCode, SubjectType.class)
            .orElseThrow((Supplier<Throwable>) () -> ServiceException.wrap("订阅类型错误"));
        dto.setNotifyType(notifyType);
        dto.setSubjectType(subjectType);
        dto.setSubjectId(subjectId);
        final List<Long> receivers = subscriptionService.getReceivers(notifyType, subjectType, subjectId);
        if (receivers.isEmpty()) {
            return;
        }
        dto.setReceivers(receivers);
        dto.setActorId(subscriptionDto.getActorId());
        notifyService.published(dto);
    }

    @Override
    @ApiOperation("查询详情")
    @Transactional(rollbackFor = Exception.class)
    public SummaryVo selectSummary(Long accountId) {
        Integer readyCount = countByReady(accountId);
        Integer unReadyCount = countByUnReady(accountId);
        Integer top5 = 5;
        final List<NotifyComplexVo> privateLetter = baseMapper.selectListByReceiverIdAndNotifyType(accountId, NotifyType.PrivateLetter.getCode(), top5)
            .stream().map(this::convertComplex).collect(Collectors.toList());
        final List<NotifyComplexVo> subscription = baseMapper.selectListByReceiverIdAndNotifyType(accountId, NotifyType.Subscription.getCode(), top5)
            .stream().map(this::convertComplex).collect(Collectors.toList());
        final List<NotifyComplexVo> announcement = baseMapper.selectListByReceiverIdAndNotifyType(accountId, NotifyType.Announcement.getCode(), top5)
            .stream().map(this::convertComplex).collect(Collectors.toList());

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

}
