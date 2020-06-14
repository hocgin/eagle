package in.hocg.eagle.modules.bmw.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.web.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * [支付网关] 事件通知列表
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bmw_notify_app")
public class NotifyApp extends AbstractEntity<NotifyApp> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("应用单号(网关): [退款单号/交易单号]")
    @TableField("request_sn")
    private String requestSn;
    @ApiModelProperty("交易单号(网关)")
    @TableField("trade_sn")
    private String tradeSn;
    @ApiModelProperty("采用的签名方式: MD5 RSA RSA2 HASH-MAC等")
    @TableField("sign_type")
    private String signType;
    @ApiModelProperty("通知事件类型，0=>支付事件; 1=>退款事件")
    @TableField("notify_type")
    private Integer notifyType;
    @ApiModelProperty("通知事件状态: 0=>初始化; 1=>进行中; 2=>成功; 3=>失败; 4=>关闭")
    @TableField("notify_status")
    private Integer notifyStatus;
    @ApiModelProperty("版本")
    @TableField("version")
    private Integer version;
    @ApiModelProperty("字符编码")
    @TableField("input_charset")
    private String inputCharset;
    @ApiModelProperty("完成通知时间")
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
