package in.hocg.eagle.modules.mms.service;

import in.hocg.eagle.basic.ext.mybatis.basic.AbstractService;
import in.hocg.eagle.basic.constant.datadict.NotifyType;
import in.hocg.eagle.basic.constant.datadict.SubjectType;
import in.hocg.eagle.modules.mms.entity.Subscription;

import java.util.List;

/**
 * <p>
 * [消息模块] 订阅列表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-04
 */
public interface SubscriptionService extends AbstractService<Subscription> {

    List<Subscription> selectListBySubjectIdAndSubjectTypeAndNotifyType(Long subjectId, Integer subjectType, Integer notifyType);

    /**
     * 获取需要通知的对象
     *
     * @param notifyType
     * @param subjectType
     * @param subjectId
     * @return
     */
    List<Long> getReceivers(NotifyType notifyType, SubjectType subjectType, Long subjectId);
}
