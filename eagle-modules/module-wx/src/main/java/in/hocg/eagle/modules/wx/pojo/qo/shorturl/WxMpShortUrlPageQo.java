package in.hocg.eagle.modules.wx.pojo.qo.shorturl;

import in.hocg.web.pojo.qo.PageQo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/5/24.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WxMpShortUrlPageQo extends PageQo {
    private String appid;
}
