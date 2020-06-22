package in.hocg.eagle.modules.bmw.service.impl;

import in.hocg.eagle.modules.bmw.entity.RequestPlatformLog;
import in.hocg.eagle.modules.bmw.mapper.RequestPlatformLogMapper;
import in.hocg.eagle.modules.bmw.service.RequestPlatformLogService;
import in.hocg.web.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * [支付网关] 所有和第三方支付交易日志表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class RequestPlatformLogServiceImpl extends AbstractServiceImpl<RequestPlatformLogMapper, RequestPlatformLog> implements RequestPlatformLogService {

}
