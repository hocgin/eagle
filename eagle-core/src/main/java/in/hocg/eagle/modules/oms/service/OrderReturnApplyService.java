package in.hocg.eagle.modules.oms.service;

import in.hocg.eagle.modules.oms.entity.OrderReturnApply;
import in.hocg.eagle.basic.AbstractService;

import java.util.Optional;

/**
 * <p>
 * 订单退货申请 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-16
 */
public interface OrderReturnApplyService extends AbstractService<OrderReturnApply> {

    Optional<OrderReturnApply> selectOneByOrderItemId(Long orderItemId);
}
