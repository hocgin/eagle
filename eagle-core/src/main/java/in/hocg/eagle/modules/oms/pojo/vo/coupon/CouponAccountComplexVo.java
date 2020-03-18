package in.hocg.eagle.modules.oms.pojo.vo.coupon;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by hocgin on 2020/3/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class CouponAccountComplexVo {
    @ApiModelProperty("用户优惠券ID")
    private Long id;
    @ApiModelProperty("拥有人")
    private Long accountId;
    @ApiModelProperty("优惠券编号")
    private String couponSn;
    @ApiModelProperty("优惠券标题")
    private String title;
    @ApiModelProperty("优惠券使用说明")
    private String instructions;
    @ApiModelProperty("使用状态：[0:未使用；1:已使用；2:已过期]")
    private Integer useStatus;
    @ApiModelProperty("优惠券生效时间")
    private LocalDateTime startAt;
    @ApiModelProperty("优惠券失效时间")
    private LocalDateTime endAt;
    @ApiModelProperty("优惠券使用时间")
    private LocalDateTime usedAt;

    /**
     * 金额相关
     */
    @ApiModelProperty("使用方式：[0:满减；1:折扣]")
    private Integer couponType;
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
    @ApiModelProperty("可用类型：[0:全场通用；1:指定品类；2:指定商品]")
    private Integer useType;
    @ApiModelProperty("可用商品品类")
    private List<Long> canUseProductCategoryId;
    @ApiModelProperty("可用商品")
    private List<Long> canUseProductId;
}
