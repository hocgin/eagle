package in.hocg.eagle.modules.wx.pojo.qo.user.tags;

import in.hocg.eagle.basic.pojo.qo.BaseQo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * Created by hocgin on 2020/5/19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WxMpUserTagsRefreshQo extends BaseQo {
    @NotBlank(message = "APP ID 不能为空")
    private String appid;
}