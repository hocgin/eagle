package in.hocg.eagle.modules.bmw.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.bmw.entity.PaymentRecord;

import java.util.List;

/**
 * <p>
 * [支付网关] 支付记录表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
public interface PaymentRecordService extends AbstractService<PaymentRecord> {

    List<PaymentRecord> selectListByTradeId(Long tradeId);
}
