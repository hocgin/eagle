package in.hocg.eagle.modules.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * [用户模块] 账号分组表
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ums_account_group")
public class AccountGroup extends AbstractEntity<AccountGroup> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("组名")
    @TableField("title")
    private String title;
    @ApiModelProperty("组描述")
    @TableField("remark")
    private String remark;
    @ApiModelProperty("分组类型, 如: 通用")
    @TableField("group_type")
    private Integer groupType;
    @ApiModelProperty("成员来源: 0->所有, 1->自定义组员列表")
    @TableField("member_source")
    private Integer memberSource;
    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;
    @ApiModelProperty("创建者")
    @TableField("creator")
    private Long creator;
    @ApiModelProperty("更新时间")
    @TableField("last_updated_at")
    private LocalDateTime lastUpdatedAt;
    @ApiModelProperty("更新者")
    @TableField("last_updater")
    private Long lastUpdater;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
