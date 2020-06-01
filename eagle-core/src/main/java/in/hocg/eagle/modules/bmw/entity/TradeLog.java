package in.hocg.eagle.modules.bmw.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import in.hocg.eagle.basic.AbstractEntity;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * <p>
 * [支付网关] 所有交易日志表
 * </p>
 *
 * @author hocgin
 * @since 2020-05-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bmw_trade_log")
public class TradeLog extends AbstractEntity<TradeLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("接入方应用ID")
    @TableField("app_id")
    private Long appId;
    @ApiModelProperty("接入方应用单号")
    @TableField("app_order_sn")
    private String appOrderSn;
    @ApiModelProperty("交易流水号")
    @TableField("transaction_sn")
    private String transactionSn;
    @ApiModelProperty("请求头")
    @TableField("request_header")
    private String requestHeader;
    @ApiModelProperty("请求参数")
    @TableField("request_params")
    private String requestParams;
    @ApiModelProperty("日志类型: payment=>支付; refund=>退款; notify=>异步通知; return=>同步通知; query=>查询")
    @TableField("log_type")
    private String logType;
    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;
    @ApiModelProperty("创建ip")
    @TableField("created_ip")
    private String createdIp;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
