package in.hocg.eagle.modules.base.pojo.qo.datadict;

import in.hocg.eagle.basic.constant.PatternConstant;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.pojo.qo.BaseQo;
import in.hocg.eagle.basic.valid.RangeEnum;
import in.hocg.eagle.modules.base.pojo.qo.datadict.item.DataDictItemInsertQo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hocgin on 2020/3/1.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class DataDictInsertQo extends BaseQo {

    @NotBlank(message = "字典名称")
    @ApiModelProperty("字典名称")
    private String title;
    @ApiModelProperty("字典备注")
    private String remark;
    @NotNull
    @ApiModelProperty("字典码")
    @Pattern(regexp = PatternConstant.ONLY_NUMBER_OR_WORD, message = "仅支持数字和字母组合")
    private String code;
    @NotNull(message = "启用状态")
    @ApiModelProperty("启用状态")
    @RangeEnum(enumClass = Enabled.class)
    private Integer enabled;
    @Valid
    private List<DataDictItemInsertQo> items = new ArrayList<>();
}
