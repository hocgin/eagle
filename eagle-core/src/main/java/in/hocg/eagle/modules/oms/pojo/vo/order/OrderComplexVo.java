package in.hocg.eagle.modules.oms.pojo.vo.order;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import in.hocg.eagle.basic.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by hocgin on 2020/3/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class OrderComplexVo {
    @ApiModelProperty("订单ID")
    private String id;
    @ApiModelProperty("订单编号")
    private String orderSn;
    @ApiModelProperty("优惠券ID")
    private Long couponId;
    @ApiModelProperty("优惠券抵扣金额")
    private BigDecimal couponAmount;
    @ApiModelProperty("运费金额")
    private BigDecimal freightAmount;
    @ApiModelProperty("管理员后台调整订单使用的折扣金额")
    private BigDecimal discountAmount;
    @ApiModelProperty("订单总金额")
    private BigDecimal totalAmount;
    @ApiModelProperty("实际支付金额")
    private BigDecimal payAmount;
    @ApiModelProperty("支付方式")
    private Integer payType;
    @ApiModelProperty("订单来源")
    private Integer sourceType;
    @ApiModelProperty("订单状态")
    private Integer orderStatus;
    @ApiModelProperty("确认收货状态")
    private Integer confirmStatus;
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
    private List<OrderItemComplexVo> orderItems = Lists.newArrayList();
}
