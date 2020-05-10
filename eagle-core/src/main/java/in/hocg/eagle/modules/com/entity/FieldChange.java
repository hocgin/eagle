package in.hocg.eagle.modules.com.entity;

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

/**
 * <p>
 * [基础模块] 业务日志-字段变更记录表
 * </p>
 *
 * @author hocgin
 * @since 2020-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("com_field_change")
public class FieldChange extends AbstractEntity<FieldChange> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("业务操作日志")
    @TableField("change_log_id")
    private Long changeLog;
    @ApiModelProperty("字段名")
    @TableField("field_name")
    private String fieldName;
    @ApiModelProperty("字段备注")
    @TableField("field_remark")
    private String fieldRemark;
    @ApiModelProperty("变更描述")
    @TableField("change_remark")
    private String changeRemark;
    @ApiModelProperty("变更前")
    @TableField("before_value")
    private String beforeValue;
    @ApiModelProperty("变更后")
    @TableField("after_value")
    private String afterValue;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
