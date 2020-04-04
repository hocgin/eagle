package in.hocg.eagle.modules.com.service;

import in.hocg.eagle.basic.aspect.logger.Logger;
import in.hocg.eagle.modules.com.entity.RequestLog;
import in.hocg.eagle.basic.AbstractService;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 * [基础模块] 请求日志表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-04
 */
public interface RequestLogService extends AbstractService<RequestLog> {

    @Async
    void asyncSave(Logger logger);
}
