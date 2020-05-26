package in.hocg.eagle.modules.com.pojo.qo.shorturl;

import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.basic.pojo.qo.Insert;
import in.hocg.eagle.basic.valid.EnumRange;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/4/4.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShortUrlSaveQo extends IdQo {
    @NotBlank(groups = {Insert.class}, message = "请输入链接")
    @ApiModelProperty("原链")
    private String originalUrl;
    @EnumRange(enumClass = Enabled.class, message = "启用状态错误")
    @NotNull(groups = {Insert.class}, message = "请选择启用状态")
    @ApiModelProperty("启用状态[0:为禁用状态;1:为正常状态]")
    private Integer enabled;
}
