package in.hocg.eagle.modules.mkt.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.constant.datadict.CouponPlatformType;
import in.hocg.eagle.basic.constant.datadict.CouponType;
import in.hocg.eagle.basic.constant.datadict.CouponUseType;
import in.hocg.eagle.basic.ext.jackson.LocalDateTimeSerializer;
import in.hocg.eagle.modules.pms.pojo.vo.category.ProductCategoryComplexVo;
import in.hocg.eagle.modules.pms.api.vo.ProductComplexVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by hocgin on 2020/3/29.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class CouponComplexVo {
    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("优惠券名称")
    private String title;
    @ApiModelProperty("折扣方式")
    private Integer couponType;
    @Named(idFor = "couponType", type = NamedType.DataDict, args = {CouponType.KEY})
    private String couponTypeName;

    @ApiModelProperty("优惠券使用说明")
    private String instructions;
    @ApiModelProperty("订单最低启用金额")
    private BigDecimal minPoint;
    @ApiModelProperty("满减金额/折扣率")
    private BigDecimal credit;
    @ApiModelProperty("优惠上限")
    private BigDecimal ceiling;
    @ApiModelProperty("可用平台：[0:全部；1:移动；2:PC]")
    private BigDecimal platform;
    @Named(idFor = "platform", type = NamedType.DataDict, args = {CouponPlatformType.KEY})
    private String platformName;


    @ApiModelProperty("可用类型：[0:全场通用；1:指定品类；2:指定商品]")
    private Integer useType;
    @Named(idFor = "useType", type = NamedType.DataDict, args = {CouponUseType.KEY})
    private String useTypeName;

    @ApiModelProperty("可用商品")
    private List<ProductComplexVo> canUseProduct = Lists.newArrayList();
    @ApiModelProperty("可用商品品类")
    private List<ProductCategoryComplexVo> canUseProductCategory = Lists.newArrayList();

    @ApiModelProperty("创建时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    @ApiModelProperty("创建人")
    private Long creator;
    @Named(idFor = "creator", type = NamedType.Nickname)
    private String creatorName;
    @ApiModelProperty("最后更新时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdatedAt;
    @ApiModelProperty("最后更新时间")
    private Long lastUpdater;
    @Named(idFor = "lastUpdater", type = NamedType.Nickname)
    private String lastUpdaterName;

}
