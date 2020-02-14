package in.hocg.eagle.modules.base.entity;


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
 * [基础模块] 数据字典项表
 * </p>
 *
 * @author hocgin
 * @since 2020-02-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_data_dict_item")
public class DataDictItem extends AbstractEntity<DataDictItem> {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * t_data_dict ID
     */
    @TableField("dict_id")
    private Integer dictId;
    /**
     * 字典项名称
     */
    @TableField("title")
    private String title;
    /**
     * 字典标识
     */
    @TableField("code")
    private String code;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 排序, 从大到小降序
     */
    @TableField("sort")
    private Integer sort;
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
