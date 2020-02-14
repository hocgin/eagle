package in.hocg.eagle.modules.account.entity;

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
 * [用户模块] 权限-账号表
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_authority_account")
public class AuthorityAccount extends AbstractEntity<AuthorityAccount> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * t_authority ID
     */
    @TableField("authority_id")
    private Integer authorityId;
    /**
     * t_account ID
     */
    @TableField("account_id")
    private Integer accountId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
