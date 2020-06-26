package in.hocg.eagle.modules.bmw.service.impl;

import in.hocg.eagle.modules.bmw.entity.NotifyAppLog;
import in.hocg.eagle.modules.bmw.mapper.NotifyAppLogMapper;
import in.hocg.eagle.modules.bmw.service.NotifyAppLogService;
import in.hocg.eagle.basic.ext.mybatis.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * <p>
 * [支付网关] 所有通知应用方日志表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class NotifyAppLogServiceImpl extends AbstractServiceImpl<NotifyAppLogMapper, NotifyAppLog> implements NotifyAppLogService {

    @Override
    public List<NotifyAppLog> selectListByNotifyAppId(Long notifyId) {
        return lambdaQuery().eq(NotifyAppLog::getNotifyAppId, notifyId).list();
    }
}
