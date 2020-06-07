package in.hocg.eagle.modules.bmw.datastruct;

import in.hocg.eagle.modules.bmw.entity.RefundRecord;
import in.hocg.eagle.modules.bmw.pojo.ro.GoRefundRo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface RefundRecordMapping {
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updateIp", ignore = true)
    @Mapping(target = "settlementRefundFee", ignore = true)
    @Mapping(target = "refundTradeNo", ignore = true)
    @Mapping(target = "refundStatus", ignore = true)
    @Mapping(target = "refundSn", ignore = true)
    @Mapping(target = "refundAt", ignore = true)
    @Mapping(target = "notifyUrl", ignore = true)
    @Mapping(target = "notifyAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdIp", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "appId", ignore = true)
    RefundRecord asRefundRecord(GoRefundRo ro);
}
