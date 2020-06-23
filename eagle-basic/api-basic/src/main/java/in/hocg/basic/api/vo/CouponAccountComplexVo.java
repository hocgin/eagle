package in.hocg.basic.api.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.web.aspect.named.InjectNamed;
import in.hocg.web.aspect.named.Named;
import in.hocg.web.aspect.named.NamedType;
import in.hocg.web.constant.datadict.CouponPlatformType;
import in.hocg.web.constant.datadict.CouponType;
import in.hocg.web.constant.datadict.CouponUseStatus;
import in.hocg.web.constant.datadict.CouponUseType;
import in.hocg.web.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Created by hocgin on 2020/3/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class CouponAccountComplexVo {
    @ApiModelProperty("用户优惠券ID")
    private Long id;
    @ApiModelProperty("拥有人")
    private Long accountId;
    @Named(idFor = "accountId", type = NamedType.Nickname)
    private String accountName;

    @ApiModelProperty("优惠券编号")
    private String couponSn;
    @ApiModelProperty("优惠券标题")
    private String title;
    @ApiModelProperty("优惠券使用说明")
    private String instructions;
    @ApiModelProperty("使用状态：[0:未使用；1:已使用；2:已过期]")
    private Integer useStatus;
    @Named(idFor = "useStatus", type = NamedType.DataDict, args = {CouponUseStatus.KEY})
    private String useStatusName;

    @ApiModelProperty("优惠券生效时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startAt;
    @ApiModelProperty("优惠券失效时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime endAt;
    @ApiModelProperty("优惠券使用时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime usedAt;
    @ApiModelProperty("领取时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    /**
     * 金额相关
     */
    @ApiModelProperty("使用方式：[0:满减；1:折扣]")
    private Integer couponType;
    @Named(idFor = "couponType", type = NamedType.DataDict, args = {CouponType.KEY})
    private String couponTypeName;

    @ApiModelProperty("满减金额/折扣率")
    private BigDecimal credit;
    @ApiModelProperty("最低启用金额")
    private BigDecimal minPoint;
    @ApiModelProperty("优惠上限金额")
    private BigDecimal ceiling;

    /**
     * 使用条件
     */
    @ApiModelProperty("可用平台：[0:全部；1:移动；2:PC]")
    private Integer platform;
    @Named(idFor = "platform", type = NamedType.DataDict, args = {CouponPlatformType.KEY})
    private String platformName;

    @ApiModelProperty("可用类型：[0:全场通用；1:指定品类；2:指定商品]")
    private Integer useType;
    @Named(idFor = "useType", type = NamedType.DataDict, args = {CouponUseType.KEY})
    private String useTypeName;

    @ApiModelProperty("可用商品品类")
    private List<ProductCategoryComplexVo> canUseProductCategory = Collections.emptyList();
    @ApiModelProperty("可用商品")
    private List<ProductComplexVo> canUseProduct = Collections.emptyList();
}
