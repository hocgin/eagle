package in.hocg.eagle.modules.com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * [基础模块] 请求日志表
 * </p>
 *
 * @author hocgin
 * @since 2020-04-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("com_request_log")
public class RequestLog extends AbstractEntity<RequestLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("请求方式")
    @TableField("method")
    private String method;
    @ApiModelProperty("请求URI")
    @TableField("uri")
    private String uri;
    @ApiModelProperty("请求体")
    @TableField("args")
    private String args;
    @ApiModelProperty("响应体")
    @TableField("ret")
    private String ret;
    @ApiModelProperty("异常信息")
    @TableField("exception")
    private String exception;
    @ApiModelProperty("线程内日志")
    @TableField("logs")
    private String logs;
    @ApiModelProperty("请求耗时(ms)")
    @TableField("total_time_millis")
    private Long totalTimeMillis;
    @ApiModelProperty("代码位置")
    @TableField("mapping")
    private String mapping;
    @ApiModelProperty("请求来源标记")
    @TableField("source")
    private String source;
    @ApiModelProperty("请求头:host")
    @TableField("host")
    private String host;
    @ApiModelProperty("请求头:user-agent")
    @TableField("user_agent")
    private String userAgent;
    @ApiModelProperty("请求IP")
    @TableField("client_ip")
    private String clientIp;
    @ApiModelProperty("国家")
    @TableField("nation")
    private String nation;
    @ApiModelProperty("省份")
    @TableField("province")
    private String province;
    @ApiModelProperty("城市")
    @TableField("city")
    private String city;
    @ApiModelProperty("运营商")
    @TableField("operator")
    private String operator;
    @ApiModelProperty("邮编")
    @TableField("zip_code")
    private String zipCode;
    @ApiModelProperty("城市编号")
    @TableField("city_code")
    private String cityCode;
    @ApiModelProperty("系统")
    @TableField("system_os")
    private String systemOs;
    @ApiModelProperty("系统版本")
    @TableField("system_version")
    private String systemVersion;
    @ApiModelProperty("平台")
    @TableField("platform")
    private String platform;
    @ApiModelProperty("内核")
    @TableField("engine")
    private String engine;
    @ApiModelProperty("内核版本")
    @TableField("engine_version")
    private String engineVersion;
    @ApiModelProperty("载体")
    @TableField("supporter")
    private String supporter;
    @ApiModelProperty("载体版本")
    @TableField("supporter_version")
    private String supporterVersion;
    @ApiModelProperty("外壳")
    @TableField("shell")
    private String shell;
    @ApiModelProperty("外壳版本")
    @TableField("shell_version")
    private String shellVersion;
    @ApiModelProperty("网络类型")
    @TableField("net_type")
    private String netType;
    @ApiModelProperty("入口描述")
    @TableField("enter_remark")
    private String enterRemark;
    @ApiModelProperty("创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;
    @ApiModelProperty("创建人")
    @TableField("creator")
    private Long creator;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
