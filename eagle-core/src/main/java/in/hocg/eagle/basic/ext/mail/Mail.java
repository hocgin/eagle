package in.hocg.eagle.basic.ext.mail;

import com.google.common.collect.Maps;
import in.hocg.eagle.basic.SpringContext;
import in.hocg.eagle.basic.ext.mail.vars.SysVars;
import in.hocg.eagle.utils.web.RequestUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

/**
 * Created by hocgin on 2020/4/6.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@RequiredArgsConstructor
public enum Mail {
    RESET_PASSWORD_MAIL("重置密码", "RESET_PASSWORD_MAIL");

    @ApiModelProperty("邮件标题")
    private final String title;
    @ApiModelProperty("邮件模版")
    private final String template;

    public Map<String, Object> getDefaultVars() {
        final Map<String, Object> vars = Maps.newHashMap();
        vars.put("sys", getSysVars());
        return vars;
    }

    /**
     * 获取系统变量
     *
     * @return
     */
    private SysVars getSysVars() {
        final Optional<HttpServletRequest> requestOpt = SpringContext.getRequest();
        final SysVars sysVars = SysVars.newDefault();
        if (requestOpt.isPresent()) {
            final HttpServletRequest request = requestOpt.get();
            sysVars.setClientIp(RequestUtils.getClientIP(request));
            sysVars.setUserAgent(RequestUtils.getUserAgent(request));
        }
        sysVars.setTitle(this.getTitle());
        sysVars.setSystemName("EAGLE");
        sysVars.setNowTime(ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return sysVars;
    }
}
