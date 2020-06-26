package in.hocg.eagle.api.pojo.qo;

import in.hocg.eagle.basic.pojo.ro.BaseRo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SignUpApiQo extends BaseRo {
    private String nickname;
    private String username;
    private String password;

}
