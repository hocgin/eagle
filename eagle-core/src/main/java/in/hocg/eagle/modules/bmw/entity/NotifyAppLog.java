package in.hocg.eagle.modules.bmw.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * [支付网关] 所有通知应用方日志表
 * </p>
 *
 * @author hocgin
 * @since 2020-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bmw_notify_app_log")
public class NotifyAppLog extends AbstractEntity<NotifyAppLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("交易流水号/退款流水号")
    @TableField("trade_no")
    private String tradeNo;
    @ApiModelProperty("接入应用订单号/退款订单号")
    @TableField("order_sn")
    private String orderSn;
    @ApiModelProperty("支付方式")
    @TableField("payment_way")
    private Integer paymentWay;
    @ApiModelProperty("采用的签名方式: MD5 RSA RSA2 HASH-MAC等")
    @TableField("sign_type")
    private String signType;
    @ApiModelProperty("退款金额")
    @TableField("total_fee")
    private BigDecimal totalFee;
    @ApiModelProperty("通知事件类型，0=>支付事件; 1=>退款事件; 2=>取消事件")
    @TableField("notify_event")
    private Integer notifyEvent;
    @ApiModelProperty("通知支付调用方结果: 0=>初始化; 1=>进行中; 2=>成功; 3=>失败")
    @TableField("notify_event_status")
    private Integer notifyEventStatus;
    @ApiModelProperty("支付时间")
    @TableField("finish_at")
    private LocalDateTime finishAt;
    @TableField("created_at")
    private LocalDateTime createdAt;
    @TableField("updated_at")
    private LocalDateTime updatedAt;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
