package in.hocg.eagle.modules.shop.pojo.vo.spec;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hocgin on 2020/3/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class SpecValueComplexVo {
    @ApiModelProperty("spec value ID")
    private Long id;
    @ApiModelProperty("规格值")
    private String value;
}
