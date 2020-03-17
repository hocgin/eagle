package in.hocg.eagle.modules.oms.pojo.dto.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/3/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class OrderItemDto {
    @ApiModelProperty("商品ID")
    private Long productId;
    @ApiModelProperty("商品图片")
    private String productPic;
    @ApiModelProperty("商品名称")
    private String productName;
    @ApiModelProperty("销售价格")
    private BigDecimal productPrice;
    @ApiModelProperty("商品SKU ID")
    private Long productSkuId;
    @ApiModelProperty("商品SKU条码")
    private String productSkuCode;
    @ApiModelProperty("商品规格")
    private String productSpecData;


    @ApiModelProperty("购买数量")
    private Integer productQuantity;

    @ApiModelProperty("优惠券优惠分解金额")
    private BigDecimal couponAmount;
    @ApiModelProperty("商品经过优惠后的金额")
    private BigDecimal realAmount;

}
