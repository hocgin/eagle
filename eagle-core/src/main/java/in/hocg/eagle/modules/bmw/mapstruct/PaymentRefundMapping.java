package in.hocg.eagle.modules.bmw.mapstruct;

import in.hocg.eagle.modules.bmw.entity.PaymentRefund;
import in.hocg.eagle.modules.bmw.pojo.qo.GoRefundQo;
import in.hocg.eagle.modules.bmw.pojo.vo.RefundInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/6/1.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface PaymentRefundMapping {

    @Mapping(target = "refundTradeNo", ignore = true)
    @Mapping(target = "refundSn", ignore = true)
    @Mapping(target = "updateIp", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "refundStatus", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdIp", ignore = true)
    PaymentRefund asPaymentRefund(GoRefundQo data);

    RefundInfo asRefundInfo(PaymentRefund refund);
}
