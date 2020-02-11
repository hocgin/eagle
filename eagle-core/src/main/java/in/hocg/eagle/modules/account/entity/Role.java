package in.hocg.eagle.modules.account.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import in.hocg.eagle.basic.AbstractEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;

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
@TableName("t_role")
public class Role extends AbstractEntity<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
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
    @TableField("description")
    private String description;
    /**
     * 启用状态[0:为禁用状态;1:为正常状态]
     */
    @TableField("enabled")
    private Integer enabled;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;
    /**
     * 创建者
     */
    @TableField("creator")
    private Integer creator;
    /**
     * 更新时间
     */
    @TableField("last_updated_at")
    private Date lastUpdatedAt;
    /**
     * 更新者
     */
    @TableField("last_updater")
    private Integer lastUpdater;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
