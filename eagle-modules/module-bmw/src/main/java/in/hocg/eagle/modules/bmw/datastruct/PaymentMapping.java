package in.hocg.eagle.modules.bmw.datastruct;

import in.hocg.eagle.modules.bmw.api.vo.GoPayVo;
import in.hocg.eagle.modules.bmw.helper.payment.pojo.response.GoPaymentResponse;
import org.mapstruct.Mapper;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface PaymentMapping {

    GoPayVo asGoPayVo(GoPaymentResponse result);
}
