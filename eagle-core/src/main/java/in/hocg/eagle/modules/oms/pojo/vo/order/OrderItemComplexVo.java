package in.hocg.eagle.modules.oms.pojo.vo.order;

import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.constant.datadict.OrderRefundApplyStatus;
import in.hocg.eagle.basic.pojo.KeyValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hocgin on 2020/3/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class OrderItemComplexVo {
    @ApiModelProperty("OrderItem ID")
    private Long id;
    @ApiModelProperty("Product ID")
    private Long productId;
    @ApiModelProperty("商品图片")
    private String productPic;
    @ApiModelProperty("商品名称")
    private String productName;
    @ApiModelProperty("商品购买时单价")
    private BigDecimal productPrice;
    @ApiModelProperty("商品购买数量")
    private Integer productQuantity;
    @ApiModelProperty("商品购买时SKU ID")
    private Long productSkuId;
    @ApiModelProperty("商品购买时SKU 编码")
    private String productSkuCode;
    private List<KeyValue> spec;
    @ApiModelProperty("商品购买规格")
    private String productSpecData;
    @ApiModelProperty("优惠券均摊金额")
    private BigDecimal couponAmount;
    @ApiModelProperty("优惠后金额")
    private BigDecimal realAmount;


    @ApiModelProperty("退费申请ID")
    private Long refundApplyId;
    @ApiModelProperty("退费申请编号")
    private String refundApplySn;

    @ApiModelProperty("退款状态")
    private Integer refundStatus;
    @Named(idFor = "refundStatus",
        type = NamedType.DataDict, args = {OrderRefundApplyStatus.KEY})
    private String refundStatusName;
}
