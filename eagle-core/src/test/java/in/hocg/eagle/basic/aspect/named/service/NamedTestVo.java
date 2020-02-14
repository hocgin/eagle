package in.hocg.eagle.basic.aspect.named.service;

import in.hocg.eagle.basic.aspect.named.Named;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class NamedTestVo {
    
    private String code;
    
    @Named(type = "type", idField = "code")
    private String codeName;
}
