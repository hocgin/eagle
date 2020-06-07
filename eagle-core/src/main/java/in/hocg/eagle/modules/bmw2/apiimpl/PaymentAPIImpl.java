package in.hocg.eagle.modules.bmw2.apiimpl;

import in.hocg.eagle.modules.bmw2.api.PaymentAPI;
import in.hocg.eagle.modules.bmw2.pojo.ro.CreateTradeRo;
import in.hocg.eagle.modules.bmw2.pojo.ro.GoPayRo;
import in.hocg.eagle.modules.bmw2.pojo.vo.GoPayVo;
import in.hocg.eagle.modules.bmw2.pojo.vo.QueryAsyncVo;
import in.hocg.eagle.modules.bmw2.pojo.vo.RefundStatusSync;
import in.hocg.eagle.modules.bmw2.pojo.vo.TradeStatusSync;
import in.hocg.eagle.modules.bmw2.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PaymentAPIImpl implements PaymentAPI {
    private final PaymentService paymentService;

    @Override
    public void closeTrade(String tradeSn) {
        paymentService.closeTrade(tradeSn);
    }

    @Override
    public String createTrade(CreateTradeRo ro) {
        return paymentService.createTrade(ro);
    }

    @Override
    public GoPayVo goPay(GoPayRo ro) {
        return paymentService.goPay(ro);
    }

    @Override
    public QueryAsyncVo<TradeStatusSync> queryTrade(String tradeSn) {
        return null;
    }

    @Override
    public QueryAsyncVo<RefundStatusSync> queryRefund(String refundSn) {
        return null;
    }
//    private final

}
