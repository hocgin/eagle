package in.hocg.eagle.modules.bmw.apiimpl;

import in.hocg.eagle.modules.bmw.api.PaymentAPI;
import in.hocg.eagle.modules.bmw.api.ro.CreateTradeRo;
import in.hocg.eagle.modules.bmw.api.ro.GoPayRo;
import in.hocg.eagle.modules.bmw.pojo.vo.GoPayVo;
import in.hocg.eagle.modules.bmw.api.vo.QueryAsyncVo;
import in.hocg.eagle.modules.bmw.api.vo.RefundStatusSync;
import in.hocg.eagle.modules.bmw.api.vo.TradeStatusSync;
import in.hocg.eagle.modules.bmw.service.PaymentService;
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
        return paymentService.queryTrade(tradeSn);
    }

    @Override
    public QueryAsyncVo<RefundStatusSync> queryRefund(String refundSn) {
        return paymentService.queryRefund(refundSn);
    }

}
