package in.hocg.eagle.modules.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * [评论模块] 评论对象表
 * </p>
 *
 * @author hocgin
 * @since 2020-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("crm_comment_target")
public class CommentTarget extends AbstractEntity<CommentTarget> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 评论对象ID
     */
    @TableField("rel_id")
    private Long relId;
    /**
     * 评论对象类型
     */
    @TableField("rel_type")
    private Integer relType;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
