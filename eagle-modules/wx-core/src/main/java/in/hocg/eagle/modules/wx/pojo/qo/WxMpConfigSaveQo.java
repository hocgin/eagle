package in.hocg.eagle.modules.wx.pojo.qo;

import in.hocg.web.constant.datadict.Enabled;
import in.hocg.web.pojo.qo.BaseQo;
import in.hocg.web.pojo.qo.Insert;
import in.hocg.web.valid.EnumRange;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * Created by hocgin on 2020/4/26.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxMpConfigSaveQo extends BaseQo {
    @NotBlank(groups = {Insert.class}, message = "AppID 不能为空")
    @ApiModelProperty("开发者ID(AppID)")
    private String appid;
    @NotBlank(groups = {Insert.class}, message = "微信公众号标题 不能为空")
    @ApiModelProperty("微信公众号标题")
    private String title;
    @NotBlank(groups = {Insert.class}, message = "AppSecret 不能为空")
    @ApiModelProperty("开发者密码(AppSecret)")
    private String appSecret;
    @NotBlank(groups = {Insert.class}, message = "Token 不能为空")
    @ApiModelProperty("令牌(Token)")
    private String token;
    @NotBlank(groups = {Insert.class}, message = "Encoding AESKey 不能为空")
    @ApiModelProperty("消息加解密密钥(EncodingAESKey)")
    private String aesKey;
    @ApiModelProperty("启用状态")
    @EnumRange(enumClass = Enabled.class)
    private Integer enabled;
}
