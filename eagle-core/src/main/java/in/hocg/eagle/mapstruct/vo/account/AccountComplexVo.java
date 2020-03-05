package in.hocg.eagle.mapstruct.vo.account;

import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

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
}
