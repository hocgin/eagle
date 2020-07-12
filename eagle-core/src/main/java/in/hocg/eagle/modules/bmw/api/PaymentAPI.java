package in.hocg.eagle.modules.bmw.api;

import in.hocg.eagle.modules.bmw.api.ro.CreateTradeRo;
import in.hocg.eagle.modules.bmw.api.ro.GoPayRo;
import in.hocg.eagle.modules.bmw.api.ro.QueryPaymentWayRo;
import in.hocg.eagle.modules.bmw.api.vo.PaymentWayVo;
import in.hocg.eagle.modules.bmw.api.vo.QueryAsyncVo;
import in.hocg.eagle.modules.bmw.api.vo.RefundStatusSync;
import in.hocg.eagle.modules.bmw.api.vo.TradeStatusSync;
import in.hocg.eagle.modules.bmw.pojo.vo.GoPayVo;

import java.util.List;

/**
 * Created by hocgin on 2020/6/6.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface PaymentAPI {

    /**
     * 关闭交易单
     *
     * @param tradeSn
     */
    void closeTrade(String tradeSn);

    /**
     * 创建交易单
     *
     * @param ro
     * @return
     */
    String createTrade(CreateTradeRo ro);

    /**
     * 查询支付方式
     *
     * @param ro
     * @return
     */
    List<PaymentWayVo> queryPaymentWay(QueryPaymentWayRo ro);

    /**
     * 去支付
     *
     * @param ro
     * @return
     */
    GoPayVo goPay(GoPayRo ro);

    /**
     * 查询交易单信息
     *
     * @param tradeSn
     * @return
     */
    QueryAsyncVo<TradeStatusSync> queryTrade(String tradeSn);

    /**
     * 查询退款单信息
     *
     * @param refundSn
     * @return
     */
    QueryAsyncVo<RefundStatusSync> queryRefund(String refundSn);
}
