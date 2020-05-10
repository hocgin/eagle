package in.hocg.eagle.modules.oms.pojo.vo.refund;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.constant.datadict.OrderRefundApplyStatus;
import in.hocg.eagle.basic.jackson.LocalDateTimeSerializer;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderItemComplexVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/3/26.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class OrderRefundApplyComplexVo {
    private Long id;
    @ApiModelProperty("申请编号")
    private String applySn;
    @ApiModelProperty("申请状态")
    private Integer applyStatus;
    @Named(idFor = "applyStatus",
        type = NamedType.DataDict, args = {OrderRefundApplyStatus.KEY})
    private String applyStatusName;
    @ApiModelProperty("订单商品ID")
    private Long orderItemId;
    @ApiModelProperty("订单商品详情")
    private OrderItemComplexVo orderItem;
    @ApiModelProperty("退货数量")
    private Integer refundQuantity;
    @ApiModelProperty("退款金额")
    private BigDecimal refundAmount;
    @ApiModelProperty("退货原因")
    private String refundReason;
    @ApiModelProperty("退货备注")
    private String refundRemark;
    @ApiModelProperty("处理备注")
    private String handleRemark;
    @ApiModelProperty("收货备注")
    private String receiveRemark;

    @ApiModelProperty("处理人")
    private Long handler;
    @Named(idFor = "handler", type = NamedType.Nickname)
    private String handlerName;

    @ApiModelProperty("处理时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime handleAt;

    @ApiModelProperty("收货人")
    private Long receiver;
    @Named(idFor = "receiver", type = NamedType.Nickname)
    private String receiverName;

    @ApiModelProperty("收货时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime receiveAt;

    private Long creator;
    @Named(idFor = "creator", type = NamedType.Nickname)
    private String creatorName;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    private Long lastUpdater;

    @Named(idFor = "lastUpdater", type = NamedType.Nickname)
    private String lastUpdaterName;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdatedAt;
}
