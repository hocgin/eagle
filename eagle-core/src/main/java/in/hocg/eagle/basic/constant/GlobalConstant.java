package in.hocg.eagle.basic.constant;

import in.hocg.eagle.basic.constant.datadict.Platform;
import lombok.experimental.UtilityClass;

/**
 * Created by hocgin on 2020/2/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class GlobalConstant {
    /**
     * 超级管理员用户名
     */
    public static final String SUPPER_ADMIN_USERNAME = "admin";
    
    /**
     * 超级管理员用户ID
     */
    public static final Long SUPPER_ADMIN_USER_ID = 1L;
    
    /**
     * 当前平台标记
     */
    public static final Platform CURRENT_PLATFORM = Platform.Eagle;
}
