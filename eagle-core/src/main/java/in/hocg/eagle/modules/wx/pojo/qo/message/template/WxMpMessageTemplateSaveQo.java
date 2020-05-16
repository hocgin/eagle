package in.hocg.eagle.modules.wx.pojo.qo.message.template;

import in.hocg.eagle.basic.pojo.qo.BaseQo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/5/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WxMpMessageTemplateSaveQo extends BaseQo {
    private Long id;

}
