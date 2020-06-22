package in.hocg.eagle.modules.wx.pojo.qo.reply;

import in.hocg.web.pojo.qo.PageQo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/5/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxReplyRulePageQo extends PageQo {
    private String appid;
}
