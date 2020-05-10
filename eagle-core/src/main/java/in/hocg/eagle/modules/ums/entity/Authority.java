package in.hocg.eagle.modules.ums.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.mybatis.tree.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

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
@TableName("ums_authority")
public class Authority extends TreeEntity<Authority> {

    private static final long serialVersionUID = 1L;

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
     * 平台编号
     */
    @TableField("platform")
    private Integer platform;
    /**
     * 排序, 从大到小降序
     */
    @TableField("sort")
    private Long sort;
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

}
