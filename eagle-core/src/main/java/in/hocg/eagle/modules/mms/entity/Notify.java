package in.hocg.eagle.modules.mms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.ext.mybatis.basic.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * [消息模块] 通知表
 * </p>
 *
 * @author hocgin
 * @since 2020-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mms_notify")
public class Notify extends AbstractEntity<Notify> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;
    /**
     * 消息文本
     */
    @TableField("content")
    private String content;
    /**
     * 标题
     */
    @TableField("title")
    private String title;
    /**
     * 触发者ID
     */
    @TableField("actor_id")
    private Long actorId;
    /**
     * 通知类型
     */
    @TableField("notify_type")
    private Integer notifyType;
    /**
     * 订阅对象类型
     */
    @TableField("subject_type")
    private Integer subjectType;
    /**
     * 订阅对象ID
     */
    @TableField("subject_id")
    private Long subjectId;
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
