package in.hocg.eagle.modules.wx.pojo.qo.material;

import in.hocg.eagle.basic.constant.datadict.wx.WxMaterialType;
import in.hocg.eagle.basic.pojo.qo.BaseQo;
import in.hocg.eagle.basic.valid.EnumRange;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/5/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class WxMaterialUploadFileQo extends BaseQo {
    @NotNull(message = "APP ID不能为空")
    private String appid;
    @NotNull(message = "文件链接不能为空")
    @URL(message = "错误的文件链接")
    private String url;
    @NotNull(message = "素材类型不能为空")
    @EnumRange(enumClass = WxMaterialType.class)
    private Integer materialType;

}
