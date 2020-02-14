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
import java.util.Date;

/**
 * <p>
 * [用户模块] 账号表
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

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 昵称;显示使用
     */
    @TableField("nickname")
    private String nickname;
    /**
     * 用户名;唯一,登录使用
     */
    @TableField("username")
    private String username;
    /**
     * 邮箱;唯一,登录使用
     */
    @TableField("email")
    private String email;
    /**
     * 手机号码;唯一,登录使用
     */
    @TableField("phone")
    private String phone;
    /**
     * 密码
     */
    @TableField("password")
    private String password;
    /**
     * 头像地址
     */
    @TableField("avatar")
    private String avatar;
    /**
     * 性别(0:女, 1:男)
     */
    @TableField("gender")
    private Integer gender;
    /**
     * 过期状态(0:为过期状态;1:为正常状态)
     */
    @TableField("expired")
    private Integer expired;
    /**
     * 锁定状态(0:为锁定状态;1:为正常状态)
     */
    @TableField("locked")
    private Integer locked;
    /**
     * 启用状态(0:为禁用状态;1:为正常状态)
     */
    @TableField("enabled")
    private Integer enabled;
    /**
     * 注册时使用的IP
     */
    @TableField("created_ip")
    private String createdIp;
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
