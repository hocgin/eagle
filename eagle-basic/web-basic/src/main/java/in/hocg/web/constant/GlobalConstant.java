package in.hocg.web.constant;

import in.hocg.web.constant.datadict.Platform;
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
    public final String SUPPER_ADMIN_USERNAME = "admin";

    /**
     * 超级管理员用户ID
     */
    public final Long SUPPER_ADMIN_USER_ID = 1L;

    /**
     * 当前平台标记
     */
    public final Platform CURRENT_PLATFORM = Platform.Eagle;
}
