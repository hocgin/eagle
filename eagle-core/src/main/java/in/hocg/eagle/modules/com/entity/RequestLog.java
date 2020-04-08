package in.hocg.eagle.modules.com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import in.hocg.eagle.basic.AbstractEntity;
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
    /**
     * 请求方式
     */
    @TableField("method")
    private String method;
    /**
     * 请求URI
     */
    @TableField("uri")
    private String uri;
    /**
     * 请求体
     */
    @TableField("args")
    private String args;
    /**
     * 响应体
     */
    @TableField("ret")
    private String ret;
    /**
     * 异常信息
     */
    @TableField("exception")
    private String exception;
    /**
     * 线程内日志
     */
    @TableField("logs")
    private String logs;
    /**
     * 请求耗时(ms)
     */
    @TableField("total_time_millis")
    private Long totalTimeMillis;
    /**
     * 代码位置
     */
    @TableField("mapping")
    private String mapping;
    /**
     * 请求来源标记
     */
    @TableField("source")
    private String source;
    /**
     * 请求头:host
     */
    @TableField("host")
    private String host;
    /**
     * 请求头:user-agent
     */
    @TableField("user_agent")
    private String userAgent;
    /**
     * 请求IP
     */
    @TableField("client_ip")
    private String clientIp;
    /**
     * 入口描述
     */
    @TableField("enter_remark")
    private String enterRemark;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;
    /**
     * 创建人
     */
    @TableField("creator")
    private Long creator;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
