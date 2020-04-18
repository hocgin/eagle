package in.hocg.eagle.modules.oms.pojo.qo.cart;

import in.hocg.eagle.basic.pojo.qo.BaseQo;
import in.hocg.eagle.basic.pojo.qo.Insert;
import in.hocg.eagle.basic.pojo.qo.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/4/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CartItemSaveQo extends BaseQo {
    @NotNull(groups = {Insert.class, Update.class}, message = "请选择商品")
    @ApiModelProperty("SKU ID")
    private Long skuId;

    @ApiModelProperty("数量")
    @NotNull(groups = {Insert.class, Update.class}, message = "请选择数量")
    @Min(groups = {Insert.class, Update.class}, value = 1, message = "数量错误")
    @Min(groups = {Insert.class, Update.class}, value = 99, message = "数量错误")
    private Integer quantity;
}
