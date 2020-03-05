package in.hocg.eagle.modules.notify.service.impl;

import in.hocg.eagle.modules.notify.entity.Subscription;
import in.hocg.eagle.modules.notify.mapper.SubscriptionMapper;
import in.hocg.eagle.modules.notify.service.SubscriptionService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

import java.util.List;

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

    @Override
    public List<Subscription> selectListBySubjectIdAndSubjectTypeAndNotifyType(Long subjectId, Integer subjectType, Integer notifyType) {
        return null;
    }
}
