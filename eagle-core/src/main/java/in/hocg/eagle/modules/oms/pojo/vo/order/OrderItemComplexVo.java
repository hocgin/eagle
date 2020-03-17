package in.hocg.eagle.modules.oms.pojo.vo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/3/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
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
    @ApiModelProperty("商品购买规格")
    private String productSpecData;
    @ApiModelProperty("优惠券均摊金额")
    private BigDecimal couponAmount;
    @ApiModelProperty("优惠后金额")
    private BigDecimal realAmount;
    @ApiModelProperty("退款状态")
    private Integer returnStatus;
}