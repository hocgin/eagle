package in.hocg.eagle.modules.bmw.helper.payment;

import in.hocg.eagle.basic.constant.datadict.RefundStatus;
import in.hocg.eagle.basic.constant.datadict.TradeStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.annotation.Nonnull;
import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/6/1.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Builder
public class SyncTradeStatusResult {
    @ApiModelProperty("交易流水号/退款流水号")
    private String tradeNo;
    @ApiModelProperty("接入应用订单号/退款订单号")
    private String orderSn;
    @ApiModelProperty("支付方式")
    private Integer paymentWay;
    @ApiModelProperty("退款金额")
    private BigDecimal totalFee;
    @ApiModelProperty("通知事件类型，0=>支付事件; 1=>退款事件; 2=>取消事件")
    private Integer notifyEvent;
    @ApiModelProperty("通知支付调用方结果: 0=>初始化; 1=>进行中; 2=>成功; 3=>失败")
    private Integer notifyEventStatus;
    @ApiModelProperty("交易状态")
    private Integer status;


    @Getter
    @ApiModel("交易状态")
    @RequiredArgsConstructor
    public enum SyncTradeStatus {
        Init(0, "初始化"),
        Pending(1, "进行中"),
        Success(2, "成功"),
        Fail(3, "失败");
        private final Integer code;
        private final String name;
    }


    public static SyncTradeStatus transform(@Nonnull RefundStatus status) {
        switch (status) {
            case Done: {
                return SyncTradeStatus.Success;
            }
            case Fail: {
                return SyncTradeStatus.Fail;
            }
            case Init: {
                return SyncTradeStatus.Init;
            }
            case Wait:
            default: {
                return SyncTradeStatus.Pending;
            }
        }
    }

    public static SyncTradeStatus transform(@NonNull TradeStatus status) {
        switch (status) {
            case Done: {
                return SyncTradeStatus.Success;
            }
            case Fail: {
                return SyncTradeStatus.Fail;
            }
            case Init: {
                return SyncTradeStatus.Init;
            }
            case Wait:
            default: {
                return SyncTradeStatus.Pending;
            }
        }
    }
}
