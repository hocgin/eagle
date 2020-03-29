package in.hocg.eagle.modules.mkt.pojo.qo;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.constant.datadict.CouponPlatformType;
import in.hocg.eagle.basic.constant.datadict.CouponType;
import in.hocg.eagle.basic.constant.datadict.CouponUseType;
import in.hocg.eagle.basic.pojo.qo.BaseQo;
import in.hocg.eagle.basic.pojo.qo.Insert;
import in.hocg.eagle.basic.pojo.qo.Update;
import in.hocg.eagle.basic.valid.RangeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hocgin on 2020/3/29.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class CouponSaveQo extends BaseQo {
    @NotNull(groups = {Update.class}, message = "ID 错误")
    @ApiModelProperty("ID")
    private Long id;

    @NotBlank(groups = {Insert.class}, message = "请输入优惠券名称")
    @ApiModelProperty("优惠券名称")
    private String title;

    @NotNull(groups = {Insert.class}, message = "请选择优惠券类型")
    @RangeEnum(groups = {Insert.class, Update.class}, enumClass = CouponType.class, message = "请选择优惠券类型")
    @ApiModelProperty("优惠券类型")
    private Integer couponType;

    @ApiModelProperty("优惠券使用说明")
    private String instructions;

    @ApiModelProperty("后台备注")
    private String remark;

    @ApiModelProperty("订单最低启用金额")
    private BigDecimal minPoint;

    @NotNull(groups = {Insert.class},message = "请输入优惠力度")
    @ApiModelProperty("满减金额/折扣率")
    private BigDecimal credit;

    @ApiModelProperty("优惠上限")
    private BigDecimal ceiling;

    @NotNull(groups = {Insert.class}, message = "请选择可用平台")
    @RangeEnum(groups = {Insert.class, Update.class}, enumClass = {CouponPlatformType.class})
    @ApiModelProperty("可用平台")
    private Integer platform;

    @NotNull(groups = {Insert.class}, message = "请选择可用类型")
    @RangeEnum(groups = {Insert.class, Update.class}, enumClass = {CouponUseType.class})
    @ApiModelProperty("可用类型")
    private Integer useType;
    @Valid
    @ApiModelProperty("可用对象")
    private List<Long> useTargetId;

}
