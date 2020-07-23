package in.hocg.eagle.modules.com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * [基础模块] 持久化消息表
 * </p>
 *
 * @author hocgin
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("com_persistence_message")
public class PersistenceMessage extends AbstractEntity<PersistenceMessage> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("消息组编号")
    @TableField("group_sn")
    private String groupSn;
    @ApiModelProperty("消息标记")
    @TableField("destination")
    private String destination;
    @ApiModelProperty("消息体")
    @TableField("payload")
    private String payload;
    @ApiModelProperty("消息体")
    @TableField("headers")
    private String headers;
    @ApiModelProperty("消息状态[0=>为准备状态;1=>已发布]")
    @TableField("published")
    private Integer published;
    @TableField("prepared_at")
    private LocalDateTime preparedAt;
    @TableField("created_at")
    private LocalDateTime createdAt;
    @TableField("updated_at")
    private LocalDateTime updatedAt;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
