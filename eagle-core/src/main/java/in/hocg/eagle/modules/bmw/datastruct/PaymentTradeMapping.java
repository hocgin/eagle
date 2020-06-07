package in.hocg.eagle.modules.bmw.datastruct;

import in.hocg.eagle.modules.bmw.entity.PaymentTrade;
import in.hocg.eagle.modules.bmw.pojo.ro.CreateTradeRo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface PaymentTradeMapping {

    @Mapping(target = "buyerPayFee", ignore = true)
    @Mapping(target = "wxOpenid", ignore = true)
    @Mapping(target = "updatedIp", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "tradeStatus", ignore = true)
    @Mapping(target = "tradeSn", ignore = true)
    @Mapping(target = "tradeNo", ignore = true)
    @Mapping(target = "paymentWay", ignore = true)
    @Mapping(target = "paymentPlatformId", ignore = true)
    @Mapping(target = "paymentAt", ignore = true)
    @Mapping(target = "notifyUrl", ignore = true)
    @Mapping(target = "notifyAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "finishAt", ignore = true)
    @Mapping(target = "expireAt", ignore = true)
    @Mapping(target = "createdIp", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "appId", ignore = true)
    PaymentTrade asPaymentTrade(CreateTradeRo ro);
}
