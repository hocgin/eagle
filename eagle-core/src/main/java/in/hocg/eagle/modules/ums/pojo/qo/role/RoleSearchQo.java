package in.hocg.eagle.modules.ums.pojo.qo.role;

import in.hocg.eagle.basic.constant.datadict.Platform;
import in.hocg.eagle.basic.pojo.ro.PageRo;
import lombok.Data;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class RoleSearchQo extends PageRo {
    private String keyword;
    private Integer platform = Platform.Eagle.getCode();
}
