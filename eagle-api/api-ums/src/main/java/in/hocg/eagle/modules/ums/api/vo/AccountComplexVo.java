package in.hocg.eagle.modules.ums.api.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.web.aspect.named.Named;
import in.hocg.web.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class AccountComplexVo implements Serializable {
    private Integer id;
    @ApiModelProperty("昵称;显示使用")
    private String nickname;
    @ApiModelProperty("用户名;唯一,登录使用")
    private String username;
    @ApiModelProperty("邮箱;唯一,登录使用")
    private String email;
    @ApiModelProperty("手机号码;唯一,登录使用")
    private String phone;
    @ApiModelProperty("头像地址")
    private String avatar;
    @ApiModelProperty("性别(0:女, 1:男)")
    private Integer gender;
    @Named(idFor = "gender")
    private String genderName;

    @ApiModelProperty("过期状态")
    private Integer expired;
    private String expiredName;

    @ApiModelProperty("锁定状态")
    private Integer locked;
    private String lockedName;

    @ApiModelProperty("启用状态")
    private Integer enabled;
    private String enabledName;

    @ApiModelProperty("创建时IP")
    private String createdIp;

    @ApiModelProperty("创建时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    @ApiModelProperty("最后更新时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdatedAt;
}
