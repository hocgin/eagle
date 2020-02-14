package in.hocg.eagle.modules.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * [用户模块] 权限表
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_authority")
public class Authority extends AbstractEntity<Authority> {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 权限名称
     */
    @TableField("title")
    private String title;
    /**
     * 权限类型 [按钮, 菜单]
     */
    @TableField("type")
    private Integer type;
    /**
     * 权限授权码
     */
    @TableField("authority_code")
    private String authorityCode;
    /**
     * 请求方式 [*, GET, POST..]
     */
    @TableField("method")
    private String method;
    /**
     * 请求URL
     */
    @TableField("uri")
    private String uri;
    /**
     * 树路径，组成方式: 父路径.当前ID
     */
    @TableField("tree_path")
    private String treePath;
    /**
     * 启用状态[0:为禁用状态;1:为正常状态]
     */
    @TableField("enabled")
    private Integer enabled;
    /**
     * 排序, 从大到小降序
     */
    @TableField("sort")
    private Integer sort;
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
