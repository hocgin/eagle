package in.hocg.eagle.modules.ums.pojo.qo.account.address;

import in.hocg.eagle.basic.constant.datadict.IsDefault;
import in.hocg.eagle.basic.pojo.qo.BaseQo;
import in.hocg.eagle.basic.pojo.qo.Insert;
import in.hocg.eagle.basic.pojo.qo.Update;
import in.hocg.eagle.basic.valid.EnumRange;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * Created by hocgin on 2020/4/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AccountAddressSaveQo extends BaseQo {
    private Long id;
    @NotBlank(groups = {Insert.class}, message = "收件人姓名不能为空")
    @ApiModelProperty("收件人姓名")
    private String name;
    @NotBlank(groups = {Insert.class}, message = "收件人手机号不能为空")
    @ApiModelProperty("收件人手机号")
    private String phone;
    @NotBlank(groups = {Insert.class}, message = "邮政编码不能为空")
    @ApiModelProperty("邮政编码")
    private String postCode;
    @NotBlank(groups = {Insert.class}, message = "省份不能为空")
    @ApiModelProperty("省份")
    private String province;
    @NotBlank(groups = {Insert.class}, message = "城市不能为空")
    @ApiModelProperty("城市")
    private String city;
    @NotBlank(groups = {Insert.class}, message = "区不能为空")
    @ApiModelProperty("区")
    private String region;
    @ApiModelProperty("详细地址(街道)")
    private String detailAddress;
    @EnumRange(groups = {Insert.class, Update.class}, enumClass = IsDefault.class)
    private Integer isDefault;

}
