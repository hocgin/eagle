package in.hocg.eagle.modules.com.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import in.hocg.eagle.basic.ext.mybatis.basic.AbstractEntity;
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
 * [基础模块] 业务操作日志表
 * </p>
 *
 * @author hocgin
 * @since 2020-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("com_change_log")
public class ChangeLog extends AbstractEntity<ChangeLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("log_sn")
    private String logSn;
    @ApiModelProperty("业务类型: 如: 订单")
    @TableField("ref_type")
    private Integer refType;
    @ApiModelProperty("业务ID: 如: 订单ID")
    @TableField("ref_id")
    private Long refId;
    @ApiModelProperty("操作类型: 0->新增, 1->修改, 2->删除")
    @TableField("change_type")
    private Integer changeType;
    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;
    @ApiModelProperty("创建人")
    @TableField("creator")
    private Long creator;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
