package in.hocg.eagle.modules.base.pojo.qo.datadict;

import in.hocg.eagle.basic.constant.PatternConstant;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.basic.valid.RangeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * Created by hocgin on 2020/3/1.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class DataDictUpdateQo extends IdQo {
    @ApiModelProperty("字典名称")
    private String title;
    @ApiModelProperty("字典备注")
    private String remark;
    @ApiModelProperty("字典码")
    @Pattern(regexp = PatternConstant.ONLY_NUMBER_OR_WORD, message = "仅支持数字和字母组合")
    private String code;
    @ApiModelProperty("启用状态")
    @RangeEnum(enumClass = Enabled.class)
    private Integer enabled;
}
