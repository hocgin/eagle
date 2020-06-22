package in.hocg.eagle.modules.ums.pojo.vo.account.address;

import in.hocg.web.aspect.named.InjectNamed;
import in.hocg.web.aspect.named.Named;
import in.hocg.web.aspect.named.NamedType;
import in.hocg.web.constant.datadict.IsDefault;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hocgin on 2020/4/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class AccountAddressComplexVo {
    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("收件人姓名")
    private String name;
    @ApiModelProperty("收件人手机号")
    private String phone;
    @ApiModelProperty("邮政编码")
    private String postCode;
    @ApiModelProperty("省份")
    private String province;
    @ApiModelProperty("城市")
    private String city;
    @ApiModelProperty("区")
    private String region;
    @ApiModelProperty("详细地址(街道)")
    private String detailAddress;
    @ApiModelProperty("是否默认")
    private Integer isDefault;
    @Named(idFor = "isDefault", type = NamedType.DataDict, args = {IsDefault.KEY})
    private String isDefaultName;
}
