package in.hocg.eagle.modules.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * [用户模块] 收货地址表
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ums_account_address")
public class AccountAddress extends AbstractEntity<AccountAddress> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("ID")
    @TableField("account_id")
    private Long accountId;
    @ApiModelProperty("收件人姓名")
    @TableField("name")
    private String name;
    @ApiModelProperty("收件人手机号")
    @TableField("phone")
    private String phone;
    @ApiModelProperty("邮政编码")
    @TableField("post_code")
    private String postCode;
    @ApiModelProperty("省份")
    @TableField("province")
    private String province;
    @ApiModelProperty("城市")
    @TableField("city")
    private String city;
    @ApiModelProperty("区域编码")
    @TableField("ad_code")
    private String adCode;
    @ApiModelProperty("区")
    @TableField("region")
    private String region;
    @ApiModelProperty("详细地址(街道)")
    @TableField("detail_address")
    private String detailAddress;
    @TableField("is_default")
    private Integer isDefault;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
