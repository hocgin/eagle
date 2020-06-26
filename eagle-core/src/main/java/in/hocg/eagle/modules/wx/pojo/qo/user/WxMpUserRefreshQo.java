package in.hocg.eagle.modules.wx.pojo.qo.user;

import in.hocg.eagle.basic.pojo.ro.BaseRo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * Created by hocgin on 2020/5/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WxMpUserRefreshQo extends BaseRo {
    @NotBlank(message = "APP ID 不能为空")
    private String appid;
}
