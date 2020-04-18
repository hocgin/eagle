package in.hocg.eagle.modules.oms.pojo.vo.cart;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.eagle.basic.aspect.named.InjectNamed;
import in.hocg.eagle.basic.aspect.named.Named;
import in.hocg.eagle.basic.aspect.named.NamedType;
import in.hocg.eagle.basic.constant.datadict.CartItemStatus;
import in.hocg.eagle.basic.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/4/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class CartItemComplexVo {
    private Long id;
    @ApiModelProperty("商品ID")
    private Long productId;
    @ApiModelProperty("加入时，商品价格")
    private BigDecimal addProductPrice;
    @ApiModelProperty("加入时，商品标题")
    private String addProductTitle;
    @ApiModelProperty("加入时，商品图片")
    private String addProductImageUrl;
    @ApiModelProperty("SKU ID")
    private Long skuId;
    @ApiModelProperty("规格属性")
    private String skuSpecData;
    @ApiModelProperty("加入的数量")
    private Integer quantity;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdatedAt;
    @ApiModelProperty("购物车商品状态")
    private Integer cartItemStatus;
    @ApiModelProperty("购物车商品状态")
    @Named(idFor = "cartItemStatus",
        type = NamedType.DataDict, args = {CartItemStatus.KEY})
    private String cartItemStatusName;
}
