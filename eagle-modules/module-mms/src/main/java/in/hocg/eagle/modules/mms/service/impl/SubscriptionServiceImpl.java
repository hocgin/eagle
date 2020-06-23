package in.hocg.eagle.modules.mms.service.impl;

import com.google.common.collect.Lists;
import in.hocg.basic.api.vo.CommentComplexVo;
import in.hocg.eagle.modules.crm.api.CommentAPI;
import in.hocg.eagle.modules.mms.entity.Subscription;
import in.hocg.eagle.modules.mms.mapper.SubscriptionMapper;
import in.hocg.eagle.modules.mms.service.SubscriptionService;
import in.hocg.web.AbstractServiceImpl;
import in.hocg.web.constant.datadict.NotifyType;
import in.hocg.web.constant.datadict.SubjectType;
import in.hocg.web.utils.ValidUtils;
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

    private final CommentAPI commentService;

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
            final CommentComplexVo comment = commentService.selectOne(subjectId);
            ValidUtils.notNull(comment);
            receivers.add(comment.getCommenterId());
        }

        receivers.addAll(selectListBySubjectIdAndSubjectTypeAndNotifyType(subjectId, subjectType.getCode(), notifyType.getCode())
            .stream().map(Subscription::getSubscriberId)
            .collect(Collectors.toList()));
        return receivers.stream().distinct().collect(Collectors.toList());
    }
}
