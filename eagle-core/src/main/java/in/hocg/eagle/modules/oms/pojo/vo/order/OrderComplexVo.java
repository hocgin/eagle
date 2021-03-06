package in.hocg.eagle.modules.oms.pojo.vo.order;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.constant.datadict.ConfirmStatus;
import in.hocg.eagle.basic.constant.datadict.OrderPayType;
import in.hocg.eagle.basic.constant.datadict.OrderSourceType;
import in.hocg.eagle.basic.constant.datadict.OrderStatus;
import in.hocg.eagle.basic.ext.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Created by hocgin on 2020/3/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class OrderComplexVo {
    @ApiModelProperty("订单ID")
    private Long id;
    @ApiModelProperty("订单拥有人ID")
    private Long accountId;
    @ApiModelProperty("订单拥有人")
    @Named(idFor = "accountId", type = NamedType.Nickname)
    private String accountName;
    @ApiModelProperty("订单编号")
    private String orderSn;
    @ApiModelProperty("交易流水号")
    private String tradeSn;
    @ApiModelProperty("优惠券ID")
    private Long couponId;

    @ApiModelProperty("运费金额")
    private BigDecimal freightAmount;
    @ApiModelProperty("优惠券抵扣金额")
    private BigDecimal couponDiscountAmount;
    @ApiModelProperty("后台调整优惠")
    private BigDecimal adjustmentDiscountAmount;
    @ApiModelProperty("[计算型]优惠总金额(不含后台调整优惠)")
    private BigDecimal discountAmount;
    @ApiModelProperty("优惠总金额")
    private BigDecimal discountTotalAmount;
    @ApiModelProperty("订单总金额")
    private BigDecimal totalAmount;
    @ApiModelProperty("实际支付金额")
    private BigDecimal payAmount;

    @ApiModelProperty("支付方式")
    private Integer payType;
    @Named(idFor = "payType",
        type = NamedType.DataDict, args = {OrderPayType.KEY})
    private String payTypeName;

    @ApiModelProperty("订单来源")
    private Integer sourceType;
    @Named(idFor = "sourceType",
        type = NamedType.DataDict, args = {OrderSourceType.KEY})
    private String sourceTypeName;

    @ApiModelProperty("订单状态")
    private Integer orderStatus;
    @Named(idFor = "orderStatus",
        type = NamedType.DataDict, args = {OrderStatus.KEY})
    private String orderStatusName;

    @ApiModelProperty("确认收货状态")
    private Integer confirmStatus;
    @Named(idFor = "confirmStatus",
        type = NamedType.DataDict, args = {ConfirmStatus.KEY})
    private String confirmStatusName;

    @ApiModelProperty("收货人姓名")
    private String receiverName;
    @ApiModelProperty("收货人电话")
    private String receiverPhone;
    @ApiModelProperty("收货人邮编")
    private String receiverPostCode;
    @ApiModelProperty("省份/直辖市")
    private String receiverProvince;
    @ApiModelProperty("城市")
    private String receiverCity;
    @ApiModelProperty("区")
    private String receiverRegion;
    @ApiModelProperty("详细地址")
    private String receiverDetailAddress;
    @ApiModelProperty("订单备注")
    private String remark;
    @ApiModelProperty("支付时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime paymentTime;
    @ApiModelProperty("发货时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime deliveryTime;
    @ApiModelProperty("确认收货时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime receiveTime;
    @ApiModelProperty("评价时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime commentTime;
    @ApiModelProperty("创建时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    @ApiModelProperty("订单项")
    private List<OrderItemComplexVo> orderItems = Collections.emptyList();
}
