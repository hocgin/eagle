package in.hocg.eagle.modules.bmw.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * [支付网关] 所有通知应用方日志表
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bmw_notify_app_log")
public class NotifyAppLog extends AbstractEntity<NotifyAppLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("通知编号ID")
    @TableField("notify_app_id")
    private Long notifyAppId;
    @ApiModelProperty("通知调用结果: 0=>初始化; 1=>响应成功; 2=>响应失败; 3=>超时失败")
    @TableField("notify_result")
    private Integer notifyResult;
    @ApiModelProperty("通知内容")
    @TableField("notify_body")
    private String notifyBody;
    @TableField("created_at")
    private LocalDateTime createdAt;
    @TableField("updated_at")
    private LocalDateTime updatedAt;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
