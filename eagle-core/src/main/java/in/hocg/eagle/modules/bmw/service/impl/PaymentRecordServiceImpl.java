package in.hocg.eagle.modules.bmw.service.impl;

import in.hocg.eagle.modules.bmw.entity.PaymentRecord;
import in.hocg.eagle.modules.bmw.mapper.PaymentRecordMapper;
import in.hocg.eagle.modules.bmw.service.PaymentRecordService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * [支付网关] 支付记录表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PaymentRecordServiceImpl extends AbstractServiceImpl<PaymentRecordMapper, PaymentRecord> implements PaymentRecordService {

}
