package in.hocg.basic.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by hocgin on 2020/3/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class SkuComplexVo {
    @ApiModelProperty("SKU ID")
    private Long id;
    @ApiModelProperty("SKU编码")
    private String skuCode;
    private Long productId;
    private String specData;
    @ApiModelProperty("价格")
    private BigDecimal price;
    @ApiModelProperty("库存")
    private Integer stock;
    @ApiModelProperty("销量")
    private Integer sale;
    @ApiModelProperty("规格图片")
    private String imageUrl;
    @ApiModelProperty("规格")
    private List<Spec> spec = Collections.emptyList();

    @Data
    public static class Spec {
        @ApiModelProperty("规格属性")
        private String key;
        @ApiModelProperty("规格值")
        private String value;
    }
}
