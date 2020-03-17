package in.hocg.eagle.modules.mms.service.impl;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.NotifyType;
import in.hocg.eagle.basic.constant.datadict.SubjectType;
import in.hocg.eagle.modules.crm.entity.Comment;
import in.hocg.eagle.modules.crm.service.CommentService;
import in.hocg.eagle.modules.mms.entity.Subscription;
import in.hocg.eagle.modules.mms.mapper.SubscriptionMapper;
import in.hocg.eagle.modules.mms.service.SubscriptionService;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * [消息模块] 订阅列表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-04
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SubscriptionServiceImpl extends AbstractServiceImpl<SubscriptionMapper, Subscription> implements SubscriptionService {

    private final CommentService commentService;

    @Override
    public List<Subscription> selectListBySubjectIdAndSubjectTypeAndNotifyType(Long subjectId, Integer subjectType, Integer notifyType) {
        return lambdaQuery().eq(Subscription::getSubjectId, subjectId)
            .eq(Subscription::getSubjectType, subjectType)
            .eq(Subscription::getNotifyType, notifyType)
            .list();
    }

    @Override
    public List<Long> getReceivers(NotifyType notifyType, SubjectType subjectType, Long subjectId) {
        List<Long> receivers = Lists.newArrayList();

        if (SubjectType.Comment.equals(subjectType)) {
            final Comment comment = commentService.getById(subjectId);
            ValidUtils.notNull(comment);
            receivers.add(comment.getCreator());
        }

        receivers.addAll(selectListBySubjectIdAndSubjectTypeAndNotifyType(subjectId, subjectType.getCode(), notifyType.getCode())
            .stream().map(Subscription::getSubscriberId)
            .collect(Collectors.toList()));
        return receivers.stream().distinct().collect(Collectors.toList());
    }
}
