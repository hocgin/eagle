package in.hocg.eagle.modules.bmw.datastruct;

import in.hocg.eagle.modules.bmw.api.vo.PaymentWayVo;
import in.hocg.eagle.modules.bmw.entity.PaymentWayRuleItem;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created by hocgin on 2020/7/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface PaymentWayRuleItemMapping {

    List<PaymentWayVo> asPaymentWayVo(List<PaymentWayRuleItem> result);

    PaymentWayVo asPaymentWayVo(PaymentWayRuleItem result);


}
