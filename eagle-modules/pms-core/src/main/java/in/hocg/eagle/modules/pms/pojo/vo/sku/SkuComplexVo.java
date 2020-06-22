package in.hocg.eagle.modules.pms.pojo.vo.sku;

import com.google.common.collect.Lists;
import in.hocg.web.pojo.qo.Insert;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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
    private Integer id;
    @ApiModelProperty("SKU编码")
    private String skuCode;
    @ApiModelProperty("价格")
    private BigDecimal price;
    @ApiModelProperty("库存")
    private Integer stock;
    @ApiModelProperty("销量")
    private Integer sale;
    @ApiModelProperty("规格图片")
    private String imageUrl;
    @ApiModelProperty("规格")
    private List<Spec> spec = Lists.newArrayList();

    @Data
    public static class Spec {
        @NotNull(groups = {Insert.class})
        @ApiModelProperty("规格属性")
        private String key;
        @NotNull(groups = {Insert.class})
        @ApiModelProperty("规格值")
        private String value;
    }
}
