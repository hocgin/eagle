package in.hocg.eagle.modules.shop.pojo.vo.spec;


import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by hocgin on 2020/3/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class SpecComplexVo {
    @ApiModelProperty("spec ID")
    private Integer id;
    @ApiModelProperty("规格名")
    private String title;
    @ApiModelProperty("规格值")
    private List<SpecValueComplexVo> values = Lists.newArrayList();
}
