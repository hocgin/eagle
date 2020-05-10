package in.hocg.eagle.modules.ums.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * [用户模块] 角色表
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ums_role")
public class Role extends AbstractEntity<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 角色名称
     */
    @TableField("title")
    private String title;
    /**
     * 角色授权码
     */
    @TableField("role_code")
    private String roleCode;
    /**
     * 角色描述
     */
    @TableField("remark")
    private String remark;
    /**
     * 启用状态[0:为禁用状态;1:为正常状态]
     */
    @TableField("enabled")
    private Integer enabled;
    /**
     * 平台编号
     */
    @TableField("platform")
    private Integer platform;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;
    /**
     * 创建者
     */
    @TableField("creator")
    private Long creator;
    /**
     * 更新时间
     */
    @TableField("last_updated_at")
    private LocalDateTime lastUpdatedAt;
    /**
     * 更新者
     */
    @TableField("last_updater")
    private Long lastUpdater;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
