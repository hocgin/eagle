package in.hocg.eagle.modules.wx.pojo.qo.shorturl;

import in.hocg.eagle.basic.pojo.ro.PageRo;
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
public class WxMpShortUrlPageQo extends PageRo {
    private String appid;
}
