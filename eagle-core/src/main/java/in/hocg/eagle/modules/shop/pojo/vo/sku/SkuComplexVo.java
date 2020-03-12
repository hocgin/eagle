package in.hocg.eagle.modules.shop.pojo.vo.sku;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

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
    @ApiModelProperty("规格值")
    private List<String> specValue = Lists.newArrayList();
    @ApiModelProperty("规格值ID")
    private List<Long> specValueId = Lists.newArrayList();
    @ApiModelProperty("价格")
    private BigDecimal price;
    @ApiModelProperty("库存")
    private Integer inventory;
    @ApiModelProperty("规格图片")
    private String imageUrl;
}
