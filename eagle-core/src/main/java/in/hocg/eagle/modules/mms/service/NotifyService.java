package in.hocg.eagle.modules.mms.service;

import in.hocg.eagle.mapstruct.dto.PublishNotifyDto;
import in.hocg.eagle.modules.mms.entity.Notify;
import in.hocg.eagle.basic.AbstractService;
import org.springframework.transaction.annotation.Transactional;

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
