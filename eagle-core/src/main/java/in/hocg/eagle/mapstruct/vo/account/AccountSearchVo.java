package in.hocg.eagle.mapstruct.vo.account;

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
    @ApiModelProperty("过期状态")
    private Integer expired;
    @ApiModelProperty("锁定状态")
    private Integer locked;
    @ApiModelProperty("启用状态")
    private Integer enabled;
    @ApiModelProperty("创建时IP")
    private String createdIp;
    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;
    @ApiModelProperty("最后更新时间")
    private LocalDateTime lastUpdatedAt;
}
