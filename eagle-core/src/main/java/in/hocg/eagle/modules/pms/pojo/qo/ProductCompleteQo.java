package in.hocg.eagle.modules.pms.pojo.qo;

import in.hocg.eagle.basic.pojo.ro.CompleteRo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/3/31.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCompleteQo extends CompleteRo {
    @ApiModelProperty("商品名称/商品ID")
    private String keyword;
}
