package in.hocg.eagle.modules.wx.pojo.qo.user.tags;

import in.hocg.web.pojo.qo.BaseQo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * Created by hocgin on 2020/5/24.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class WxMpUserTagsInsertQo extends BaseQo {
    @NotBlank(message = "APP ID 不能为空")
    private String appid;
    @NotBlank(message = "标签名称不能为空")
    private String name;
}
