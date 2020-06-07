package in.hocg.eagle.modules.bmw2.service;

import in.hocg.eagle.modules.bmw2.entity.RefundRecord;
import in.hocg.eagle.basic.AbstractService;

import java.util.Optional;

/**
 * <p>
 * [支付网关] 退款记录表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
public interface RefundRecordService extends AbstractService<RefundRecord> {

    Optional<RefundRecord> selectOneByRefundSn(String refundSn);

    boolean updateOneByIdAndTradeStatus(RefundRecord updated, Long id, Integer refundStatus);
}
