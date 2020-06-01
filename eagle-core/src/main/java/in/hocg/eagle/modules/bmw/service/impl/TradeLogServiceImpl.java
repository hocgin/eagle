package in.hocg.eagle.modules.bmw.service.impl;

import in.hocg.eagle.modules.bmw.entity.TradeLog;
import in.hocg.eagle.modules.bmw.mapper.TradeLogMapper;
import in.hocg.eagle.modules.bmw.service.TradeLogService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * [支付网关] 所有交易日志表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-30
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class TradeLogServiceImpl extends AbstractServiceImpl<TradeLogMapper, TradeLog> implements TradeLogService {

}
