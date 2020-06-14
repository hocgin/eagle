package in.hocg.eagle.modules.com.pojo.vo.requestlog;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.web.aspect.named.InjectNamed;
import in.hocg.web.aspect.named.Named;
import in.hocg.web.aspect.named.NamedType;
import in.hocg.web.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/4/4.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
public class RequestLogComplexVo {
    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("请求方式")
    private String method;
    @ApiModelProperty("请求URI")
    private String uri;
    @ApiModelProperty("请求体")
    private String args;
    @ApiModelProperty("响应体")
    private String ret;
    @ApiModelProperty("异常信息")
    private String exception;
    @ApiModelProperty("线程内日志")
    private String logs;
    @ApiModelProperty("请求耗时(ms)")
    private Long totalTimeMillis;
    @ApiModelProperty("代码位置")
    private String mapping;
    @ApiModelProperty("请求头:host")
    private String host;
    @ApiModelProperty("请求头:user-agent")
    private String userAgent;
    @ApiModelProperty("请求IP")
    private String clientIp;
    @ApiModelProperty("国家")
    private String nation;
    @ApiModelProperty("省份")
    private String province;
    @ApiModelProperty("城市")
    private String city;
    @ApiModelProperty("运营商")
    private String operator;
    @ApiModelProperty("邮编")
    private String zipCode;
    @ApiModelProperty("城市编号")
    private String cityCode;
    @ApiModelProperty("系统")
    private String systemOs;
    @ApiModelProperty("系统版本")
    private String systemVersion;
    @ApiModelProperty("平台")
    private String platform;
    @ApiModelProperty("内核")
    private String engine;
    @ApiModelProperty("内核版本")
    private String engineVersion;
    @ApiModelProperty("载体")
    private String supporter;
    @ApiModelProperty("载体版本")
    private String supporterVersion;
    @ApiModelProperty("外壳")
    private String shell;
    @ApiModelProperty("外壳版本")
    private String shellVersion;
    @ApiModelProperty("网络类型")
    private String netType;
    @ApiModelProperty("入口描述")
    private String enterRemark;
    @ApiModelProperty("创建时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    @ApiModelProperty("创建人")
    private Long creator;
    @ApiModelProperty("创建人名")
    @Named(idFor = "creator", type = NamedType.Nickname)
    private String creatorName;
}
