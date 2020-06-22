package in.hocg.eagle.modules.mms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.web.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * [消息模块] 通知-接收人表
 * </p>
 *
 * @author hocgin
 * @since 2020-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("mms_notification")
public class Notification extends AbstractEntity<Notification> {

    private static final long serialVersionUID = 1L;

    /**
     * 通知ID
     */
    @TableId("notify_id")
    private Long notifyId;
    /**
     * 接收人ID
     */
    @TableField("receiver_id")
    private Long receiverId;

    @TableField("read_at")
    private LocalDateTime readAt;


    @Override
    public Serializable pkVal() {
        return this.notifyId;
    }

}
