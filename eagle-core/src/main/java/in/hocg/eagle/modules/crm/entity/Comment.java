package in.hocg.eagle.modules.crm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.web.mybatis.tree.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("crm_comment")
public class Comment extends TreeEntity<Comment> {

    private static final long serialVersionUID = 1L;

    /**
     * 评论对象ID
     */
    @TableField("target_id")
    private Long targetId;
    /**
     * 评论内容
     */
    @TableField("content")
    private String content;
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

}
