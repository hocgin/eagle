package in.hocg.eagle.modules.wx.pojo.qo.message.template;

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
public class WxMpMessageTemplatePageQo extends PageQo {
    private String appid;
}
