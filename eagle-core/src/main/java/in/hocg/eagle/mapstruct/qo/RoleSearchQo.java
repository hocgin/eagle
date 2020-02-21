package in.hocg.eagle.mapstruct.qo;

import in.hocg.eagle.basic.constant.Platform;
import in.hocg.eagle.basic.qo.PageQo;
import lombok.Data;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class RoleSearchQo extends PageQo {
    
    private Integer platform = Platform.Eagle.getCode();
}
