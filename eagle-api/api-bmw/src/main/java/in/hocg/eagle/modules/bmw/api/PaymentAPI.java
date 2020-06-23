package in.hocg.eagle.modules.bmw.api;


import in.hocg.eagle.modules.bmw.api.ro.CreateTradeRo;
import in.hocg.eagle.modules.bmw.api.ro.GoPayRo;
import in.hocg.eagle.modules.bmw.api.vo.GoPayVo;
import in.hocg.eagle.modules.bmw.api.vo.QueryAsyncVo;
import in.hocg.eagle.modules.bmw.api.vo.RefundStatusSync;
import in.hocg.eagle.modules.bmw.api.vo.TradeStatusSync;

/**
 * Created by hocgin on 2020/6/6.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface PaymentAPI {

    void closeTrade(String tradeSn);

    String createTrade(CreateTradeRo ro);

    GoPayVo goPay(GoPayRo ro);

    QueryAsyncVo<TradeStatusSync> queryTrade(String tradeSn);

    QueryAsyncVo<RefundStatusSync> queryRefund(String refundSn);
}
