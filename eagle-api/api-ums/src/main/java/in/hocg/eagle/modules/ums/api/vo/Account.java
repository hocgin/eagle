package in.hocg.eagle.modules.ums.api.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * [用户模块] 账号表
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Data
@Accessors(chain = true)
public class Account {


    /**
     * ID
     */
    private Long id;
    /**
     * 昵称;显示使用
     */
    private String nickname;
    /**
     * 用户名;唯一,登录使用
     */
    private String username;
    /**
     * 邮箱;唯一,登录使用
     */
    private String email;
    /**
     * 手机号码;唯一,登录使用
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 性别(0:女, 1:男)
     */
    private Integer gender;
    /**
     * 过期状态(0:为过期状态;1:为正常状态)
     */
    private Integer expired;
    /**
     * 锁定状态(0:为锁定状态;1:为正常状态)
     */
    private Integer locked;
    /**
     * 启用状态(0:为禁用状态;1:为正常状态)
     */
    private Integer enabled;
    /**
     * 注册时使用的IP
     */
    private String createdIp;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 创建者
     */
    private Long creator;
    /**
     * 更新时间
     */
    private LocalDateTime lastUpdatedAt;
    /**
     * 更新者
     */
    private Long lastUpdater;

}
