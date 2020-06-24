package in.hocg.eagle.modules.ums.pojo.vo.account.group;

import in.hocg.basic.named.InjectNamed;
import in.hocg.basic.named.Named;
import in.hocg.basic.named.NamedType;
import in.hocg.web.constant.datadict.Enabled;
import in.hocg.web.constant.datadict.Expired;
import in.hocg.web.constant.datadict.Locked;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hocgin on 2020/4/19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class AccountGroupMemberComplexVo {
    @ApiModelProperty("成员ID")
    private Long accountId;
    private String accountName;
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

}
