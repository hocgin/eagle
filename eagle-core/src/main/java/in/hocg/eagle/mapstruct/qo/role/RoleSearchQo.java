package in.hocg.eagle.mapstruct.qo.role;

import in.hocg.eagle.basic.constant.datadict.Platform;
import in.hocg.eagle.basic.pojo.qo.PageQo;
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
