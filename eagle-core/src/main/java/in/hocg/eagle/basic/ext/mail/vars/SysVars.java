package in.hocg.eagle.basic.ext.mail.vars;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by hocgin on 2020/4/6.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class SysVars {
    @ApiModelProperty("邮件标题")
    private String title;
    @ApiModelProperty("系统名称")
    private String systemName;
    @ApiModelProperty("请求 IP")
    private String clientIp;
    @ApiModelProperty("请求 User-Agent")
    private String userAgent;
    @ApiModelProperty("当前时间")
    private String nowTime;

    public static SysVars newDefault() {
        final SysVars sysVars = new SysVars();
        sysVars.setTitle("N/A");
        sysVars.setClientIp("N/A");
        sysVars.setSystemName("N/A");
        sysVars.setUserAgent("N/A");
        sysVars.setNowTime("N/A");
        return sysVars;
    }
}
