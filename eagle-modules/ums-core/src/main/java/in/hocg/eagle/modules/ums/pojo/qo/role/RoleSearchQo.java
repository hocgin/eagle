package in.hocg.eagle.modules.ums.pojo.qo.role;

import in.hocg.web.constant.datadict.Platform;
import in.hocg.web.pojo.qo.PageQo;
import lombok.Data;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class RoleSearchQo extends PageQo {
    private String keyword;
    private Integer platform = Platform.Eagle.getCode();
}
