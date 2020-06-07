package in.hocg.eagle.modules.bmw2.datastruct;

import in.hocg.eagle.modules.bmw2.helper.payment.request.PaymentRequestResult;
import in.hocg.eagle.modules.bmw2.pojo.vo.GoPayVo;
import org.mapstruct.Mapper;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface PaymentMapping {

    GoPayVo asGoPayVo(PaymentRequestResult result);
}
