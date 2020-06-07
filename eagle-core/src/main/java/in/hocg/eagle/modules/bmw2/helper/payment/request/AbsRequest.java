package in.hocg.eagle.modules.bmw2.helper.payment.request;

import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.basic.env.Env;
import in.hocg.eagle.utils.web.RequestUtils;

/**
 * Created by hocgin on 2020/6/1.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public abstract class AbsRequest {

    protected String getHost() {
        return Env.getConfigs().getHostname();
    }

    protected String getClientIp() {
        return SpringContext.getRequest().map(RequestUtils::getClientIP).orElse(null);
    }
}
