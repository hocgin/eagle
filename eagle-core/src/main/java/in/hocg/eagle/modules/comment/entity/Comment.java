package in.hocg.eagle.modules.comment.entity;

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
 * [评论模块] 评论表
 * </p>
 *
 * @author hocgin
 * @since 2020-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_comment")
public class Comment extends AbstractEntity<Comment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("parent_id")
    private Long parentId;
    /**
     * 评论对象ID
     */
    @TableField("target_id")
    private Long targetId;
    /**
     * 树路径，组成方式: /父路径/当前ID
     */
    @TableField("tree_path")
    private String treePath;
    /**
     * 评论内容
     */
    @TableField("content")
    private String content;
    /**
     * 启用状态[0:为禁用状态;1:为正常状态]
     */
    @TableField("enabled")
    private Integer enabled;
    /**
     * 评论人
     */
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
