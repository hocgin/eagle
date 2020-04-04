package in.hocg.eagle.modules.com.service.impl;

import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.aspect.logger.Logger;
import in.hocg.eagle.modules.com.entity.RequestLog;
import in.hocg.eagle.modules.com.mapper.RequestLogMapper;
import in.hocg.eagle.modules.com.service.RequestLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <p>
 * [基础模块] 请求日志表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-04
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class RequestLogServiceImpl extends AbstractServiceImpl<RequestLogMapper, RequestLog>
    implements RequestLogService {

    @Async
    @Override
    public void asyncSave(Logger logger) {
        RequestLog entity = logger.asRequestLog();
        baseMapper.insert(entity);
    }
}
