package in.hocg.eagle.modules.bmw.mapstruct;

import in.hocg.eagle.modules.bmw.entity.PaymentTransaction;
import in.hocg.eagle.modules.bmw.pojo.qo.CreatePaymentTransactionQo;
import in.hocg.eagle.modules.bmw.pojo.vo.TransactionInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/5/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface PaymentTransactionMapping {

    @Mapping(target = "tradeStatus", ignore = true)
    @Mapping(target = "paymentWay", ignore = true)
    @Mapping(target = "tradeNo", source = "")
    @Mapping(target = "finishAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedIp", ignore = true)
    @Mapping(target = "paymentAt", ignore = true)
    @Mapping(target = "notifyAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdIp", ignore = true)
    @Mapping(target = "expireAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    PaymentTransaction asPaymentTransaction(CreatePaymentTransactionQo data);

    TransactionInfo asTransactionInfo(PaymentTransaction transaction);
}
