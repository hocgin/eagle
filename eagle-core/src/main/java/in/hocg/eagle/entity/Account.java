package in.hocg.eagle.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import in.hocg.eagle.basic.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_account")
public class Account extends AbstractEntity<Account> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("created_at")
    private Date createdAt;
    @TableField("modified_at")
    private Date modifiedAt;
    @TableField("name")
    private String name;
    @TableField("settings")
    private String settings;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
