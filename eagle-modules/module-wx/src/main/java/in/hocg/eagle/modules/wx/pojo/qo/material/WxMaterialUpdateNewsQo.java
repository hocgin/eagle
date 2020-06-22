package in.hocg.eagle.modules.wx.pojo.qo.material;

import in.hocg.web.pojo.qo.BaseQo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/5/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxMaterialUpdateNewsQo extends BaseQo {
    private Long id;
    @NotNull(message = "下标错误")
    private Integer index;

    @Valid
    @NotNull(message = "文章不能为空")
    private WxMaterialUploadNewsQo.NewsItem item;

}
