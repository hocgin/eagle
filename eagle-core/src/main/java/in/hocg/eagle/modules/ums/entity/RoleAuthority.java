package in.hocg.eagle.modules.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * [用户模块] 角色-权限表
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ums_role_authority")
public class RoleAuthority extends AbstractEntity<RoleAuthority> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * ums_role ID
     */
    @TableField("role_id")
    private Long roleId;
    /**
     * ums_authority ID
     */
    @TableField("authority_id")
    private Long authorityId;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
