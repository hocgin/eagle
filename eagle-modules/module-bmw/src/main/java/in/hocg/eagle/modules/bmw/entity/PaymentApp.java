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
 * [支付网关] 接入方表
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bmw_payment_app")
public class PaymentApp extends AbstractEntity<PaymentApp> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("接入方编号")
    @TableField("app_sn")
    private Long appSn;
    @ApiModelProperty("标题")
    @TableField("title")
    private String title;
    @ApiModelProperty("启用状态[0:为禁用状态;1:为正常状态]")
    @TableField("enabled")
    private Integer enabled;
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
