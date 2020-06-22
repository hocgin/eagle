package in.hocg.eagle.modules.oms.pojo.qo.order;

import in.hocg.web.constant.CodeEnum;
import in.hocg.web.constant.datadict.OrderPayType;
import in.hocg.web.constant.datadict.PaymentPlatform;
import in.hocg.web.exception.ServiceException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class AsyncOrderMessageQo {
    @ApiModelProperty("平台")
    private Integer platformType;
    @ApiModelProperty("状态同步")
    private TradeStatusSync data;

    @Data
    public static class TradeStatusSync {
        @ApiModelProperty("支付方式")
        private Integer paymentWay;
        @ApiModelProperty("交易单号")
        private String tradeSn;
        @ApiModelProperty("商户单号")
        private String outTradeSn;
        @ApiModelProperty("交易状态")
        private Integer tradeStatus;
        @ApiModelProperty("交易金额")
        private BigDecimal totalFee;
        @ApiModelProperty("付款时间")
        private LocalDateTime paymentAt;
        @ApiModelProperty("(微信用户OPEN-ID)仅微信")
        private String openid;

    }

    public OrderPayType getPayType() {
        final PaymentPlatform platform = CodeEnum.of(platformType, PaymentPlatform.class)
            .orElseThrow(() -> ServiceException.wrap("错误的支付方式"));
        switch (platform) {
            case AliPay: {
                return OrderPayType.AliPay;
            }
            case WxPay: {
                return OrderPayType.WxPay;
            }
            default:
                throw new UnsupportedOperationException("错误的支付方式");
        }
    }
}
