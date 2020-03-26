package in.hocg.eagle.modules.oms.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.oms.entity.OrderRefundApply;
import in.hocg.eagle.modules.oms.pojo.qo.order.RefundApplyQo;

import java.util.Optional;

/**
 * <p>
 * 订单退货申请 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-16
 */
public interface OrderRefundApplyService extends AbstractService<OrderRefundApply> {

    Optional<OrderRefundApply> selectOneByOrderItemId(Long orderItemId);

    /**
     * 申请退款
     *
     * @param qo
     */
    void applyRefund(RefundApplyQo qo);
}
