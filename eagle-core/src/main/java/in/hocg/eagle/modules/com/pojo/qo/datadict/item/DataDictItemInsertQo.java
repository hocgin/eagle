package in.hocg.eagle.modules.com.pojo.qo.datadict.item;

import in.hocg.eagle.basic.constant.PatternConstant;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.pojo.qo.BaseQo;
import in.hocg.eagle.basic.valid.RangeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by hocgin on 2020/3/2.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class DataDictItemInsertQo extends BaseQo {
    @ApiModelProperty("排序")
    private Integer sort;
    @NotNull(message = "启用状态")
    @RangeEnum(enumClass = Enabled.class)
    @ApiModelProperty("启用状态")
    private Integer enabled;
    @NotNull
    @ApiModelProperty("字典标识")
    @Pattern(regexp = PatternConstant.ONLY_NUMBER_OR_WORD, message = "仅支持数字和字母组合")
    private String code;
    @ApiModelProperty("字典备注")
    private String remark;
    @NotNull
    @ApiModelProperty("字典项名称")
    private String title;
}
