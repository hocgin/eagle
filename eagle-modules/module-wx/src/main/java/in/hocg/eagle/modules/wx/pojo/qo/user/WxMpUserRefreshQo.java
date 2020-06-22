package in.hocg.eagle.modules.wx.pojo.qo.user;

import in.hocg.web.pojo.qo.BaseQo;
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
public class WxMpUserRefreshQo extends BaseQo {
    @NotBlank(message = "APP ID 不能为空")
    private String appid;
}