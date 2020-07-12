package in.hocg.eagle.modules.oms.pojo.dto.cart;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/7/4.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class CartItemSaveDto {
    @NotNull(message = "SKU ID")
    @ApiModelProperty("SKU ID")
    private Long skuId;

    @NotNull(message = "数量")
    @ApiModelProperty("数量")
    private Integer quantity;

    @NotNull(message = "操作时间")
    @ApiModelProperty("操作时间")
    private LocalDateTime operationAt;

    @NotNull(message = "操作人")
    @ApiModelProperty("操作人")
    private Long operatorId;
}
