package in.hocg.eagle.modules.oms.pojo.vo.refund;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.eagle.basic.jackson.LocalDateTimeSerializer;
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
public class OrderRefundApplyComplexVo {
    private Long id;
    @ApiModelProperty("申请编号")
    private String applySn;
    @ApiModelProperty("申请状态")
    private Integer applyStatus;
    @ApiModelProperty("订单商品ID")
    private Long orderItemId;
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
    @ApiModelProperty("处理时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime handleAt;
    @ApiModelProperty("收货人")
    private Long receiver;
    @ApiModelProperty("收货时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime receiveAt;
    private Long creator;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    private Long lastUpdater;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdatedAt;
}
