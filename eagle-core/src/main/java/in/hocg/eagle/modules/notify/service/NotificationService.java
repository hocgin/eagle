package in.hocg.eagle.modules.notify.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.mapstruct.qo.notify.PublishPrivateLetterQo;
import in.hocg.eagle.mapstruct.qo.notify.PublishSubscriptionDto;
import in.hocg.eagle.mapstruct.qo.notify.SearchNotifyPagingQo;
import in.hocg.eagle.mapstruct.vo.notify.NotifyComplexVo;
import in.hocg.eagle.mapstruct.vo.notify.SummaryVo;
import in.hocg.eagle.modules.notify.entity.Notification;

/**
 * <p>
 * [消息模块] 通知-接收人表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-04
 */
public interface NotificationService extends AbstractService<Notification> {

    /**
     * 查询消息列表
     *
     * @param qo
     * @return
     */
    IPage<NotifyComplexVo> search(SearchNotifyPagingQo qo);

    /**
     * 发布私信
     *
     * @param qo
     */
    void publishPrivateLetter(PublishPrivateLetterQo qo);

    /**
     * 发布通知
     *
     * @param dto
     * @throws Throwable
     */
    void publishSubscription(PublishSubscriptionDto dto) throws Throwable;

    /**
     * 获取概述
     *
     * @param currentUserId
     * @return
     */
    SummaryVo selectSummary(Long currentUserId);
}
