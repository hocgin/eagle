package in.hocg.eagle.modules.notify.service.impl;

import in.hocg.eagle.modules.notify.entity.Notify;
import in.hocg.eagle.modules.notify.mapper.NotifyMapper;
import in.hocg.eagle.modules.notify.service.NotifyService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

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

}
