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
import java.time.LocalDateTime;

/**
 * <p>
 * [支付网关] 支付记录表
 * </p>
 *
 * @author hocgin
 * @since 2020-05-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bmw_payment_record")
public class PaymentRecord extends AbstractEntity<PaymentRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("交易流水ID")
    @TableField("transaction_id")
    private Long transactionId;
    @ApiModelProperty("支付渠道")
    @TableField("payment_way")
    private Integer paymentWay;
    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;
    @ApiModelProperty("创建的IP")
    @TableField("created_ip")
    private String createdIp;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
