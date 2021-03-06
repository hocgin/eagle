package in.hocg.eagle.modules.mms.service;

import in.hocg.eagle.basic.ext.mybatis.core.AbstractService;
import in.hocg.eagle.modules.mms.entity.Notify;
import in.hocg.eagle.modules.mms.pojo.dto.notify.PublishNotifyDto;

/**
 * <p>
 * [消息模块] 通知表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-04
 */
public interface NotifyService extends AbstractService<Notify> {

    void published(PublishNotifyDto dto);
}
