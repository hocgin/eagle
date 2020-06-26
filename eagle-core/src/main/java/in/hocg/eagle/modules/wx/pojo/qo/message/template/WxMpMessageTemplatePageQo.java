package in.hocg.eagle.modules.wx.pojo.qo.message.template;

import in.hocg.eagle.basic.pojo.ro.PageRo;
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
public class WxMpMessageTemplatePageQo extends PageRo {
    private String appid;
}
