package in.hocg.eagle.modules.wx.pojo.qo.user;

import in.hocg.web.pojo.qo.PageQo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/5/10.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxMpUserPagingQo extends PageQo {
    private String appid;
}
