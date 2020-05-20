package in.hocg.eagle.modules.wx.pojo.qo.user.tags;

import in.hocg.eagle.basic.pojo.qo.BaseQo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by hocgin on 2020/5/19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class WxMpUnsetUserTagsQo extends BaseQo {
    private Long tagsId;
    @NotNull(message = "请选择微信用户")
    @Size(min = 1, message = "请选择微信用户")
    private List<Long> wxUserId;
}
