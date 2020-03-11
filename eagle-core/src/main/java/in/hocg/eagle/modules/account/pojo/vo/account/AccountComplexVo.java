package in.hocg.eagle.modules.account.pojo.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.jackson.LocalDateTimeSerializer;
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
@InjectNamed
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
    @Named(idFor = "expired", type = NamedType.DataDict)
    private String expiredName;

    @ApiModelProperty("锁定状态")
    private Integer locked;
    @Named(idFor = "locked", type = NamedType.DataDict)
    private String lockedName;

    @ApiModelProperty("启用状态")
    private Integer enabled;
    @Named(idFor = "enabled", type = NamedType.DataDict)
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
