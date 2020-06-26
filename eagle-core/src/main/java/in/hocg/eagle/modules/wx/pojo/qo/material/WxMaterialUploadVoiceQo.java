package in.hocg.eagle.modules.wx.pojo.qo.material;

import in.hocg.eagle.basic.pojo.ro.BaseRo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/5/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxMaterialUploadVoiceQo extends BaseRo {
    @NotNull(message = "APP ID不能为空")
    private String appid;
    @NotNull(message = "文件链接不能为空")
    @URL(message = "错误的文件链接")
    private String url;

}
