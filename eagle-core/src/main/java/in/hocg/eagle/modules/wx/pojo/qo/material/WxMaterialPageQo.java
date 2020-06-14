package in.hocg.eagle.modules.wx.pojo.qo.material;

import in.hocg.web.constant.datadict.WxMaterialType;
import in.hocg.web.pojo.qo.PageQo;
import in.hocg.web.valid.EnumRange;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/5/6.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WxMaterialPageQo extends PageQo {
    private String appid;
    @EnumRange(enumClass = WxMaterialType.class, message = "素材类型错误")
    private Integer materialType;
}
