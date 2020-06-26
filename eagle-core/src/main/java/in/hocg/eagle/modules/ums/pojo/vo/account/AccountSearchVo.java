package in.hocg.eagle.modules.ums.pojo.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.constant.datadict.Expired;
import in.hocg.eagle.basic.constant.datadict.Gender;
import in.hocg.eagle.basic.constant.datadict.Locked;
import in.hocg.eagle.basic.ext.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/3/3.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class AccountSearchVo {
    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("手机号码")
    private String phone;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("性别")
    private Integer gender;
    @Named(idFor = "gender", type = NamedType.DataDict, args = {Gender.KEY})
    private String genderName;

    @ApiModelProperty("过期状态")
    private Integer expired;
    @Named(idFor = "expired", type = NamedType.DataDict, args = {Expired.KEY})
    private String expiredName;

    @ApiModelProperty("锁定状态")
    private Integer locked;
    @Named(idFor = "locked", type = NamedType.DataDict, args = {Locked.KEY})
    private String lockedName;

    @ApiModelProperty("启用状态")
    private Integer enabled;
    @Named(idFor = "enabled", type = NamedType.DataDict, args = {Enabled.KEY})
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
