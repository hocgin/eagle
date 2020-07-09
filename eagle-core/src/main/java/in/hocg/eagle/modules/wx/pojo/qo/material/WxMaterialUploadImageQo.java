package in.hocg.eagle.modules.wx.pojo.qo.material;

import in.hocg.eagle.basic.constant.PatternConstant;
import in.hocg.eagle.basic.pojo.ro.BaseRo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by hocgin on 2020/5/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxMaterialUploadImageQo extends BaseRo {
    @NotNull(message = "APP ID不能为空")
    private String appid;
    @NotNull(message = "文件链接不能为空")
    @Pattern(regexp = PatternConstant.URL,message = "错误的文件链接")
    private String url;

}
