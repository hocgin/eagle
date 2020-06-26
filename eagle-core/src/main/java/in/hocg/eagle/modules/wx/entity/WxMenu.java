package in.hocg.eagle.modules.wx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.ext.mybatis.basic.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * [微信模块] 菜单表
 * </p>
 *
 * @author hocgin
 * @since 2020-05-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wx_menu")
public class WxMenu extends AbstractEntity<WxMenu> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("微信菜单组命名")
    @TableField("title")
    private String title;
    @ApiModelProperty("APP ID")
    @TableField("appid")
    private String appid;
    @ApiModelProperty("上传个性化菜单后的 menu_id (仅菜单类型为: 个性化菜单)")
    @TableField("menu_id")
    private String menuId;
    @ApiModelProperty("菜单对象数组")
    @TableField("button")
    private String button;
    @ApiModelProperty("菜单匹配规则(仅菜单类型为: 个性化菜单)")
    @TableField("match_rule")
    private String matchRule;
    @ApiModelProperty("菜单类型, 0->通用菜单; 1->个性化菜单")
    @TableField("menu_type")
    private Integer menuType;
    @ApiModelProperty("启用状态[0:为禁用状态;1:为正常状态]")
    @TableField("enabled")
    private Integer enabled;

    @TableField("uploader")
    private Long uploader;
    @TableField("uploaded_at")
    private LocalDateTime uploadedAt;
    @TableField("creator")
    private Long creator;
    @TableField("created_at")
    private LocalDateTime createdAt;
    @TableField("last_updater")
    private Long lastUpdater;
    @TableField("last_updated_at")
    private LocalDateTime lastUpdatedAt;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
