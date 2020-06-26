package in.hocg.eagle.modules.com.pojo.qo.systemsettings;

import in.hocg.eagle.basic.pojo.ro.BaseRo;
import in.hocg.eagle.basic.pojo.ro.Insert;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by hocgin on 2020/4/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class SystemSettingsSaveQo extends BaseRo {
    @ApiModelProperty("ID")
    private Long id;
    @NotBlank(groups = {Insert.class}, message = "配置名称不能为空")
    @ApiModelProperty("配置名称")
    private String title;
    @NotBlank(groups = {Insert.class}, message = "配置备注不能为空")
    @ApiModelProperty("配置备注")
    private String remark;
    @NotBlank(groups = {Insert.class}, message = "配置码不能为空")
    @ApiModelProperty("配置码")
    private String configCode;
    @NotBlank(groups = {Insert.class}, message = "配置值不能为空")
    @ApiModelProperty("配置值")
    private String value;
}
