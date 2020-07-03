package in.hocg.eagle.basic.aspect.logger;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import in.hocg.eagle.basic.ext.security.User;
import in.hocg.eagle.basic.ext.web.SpringContext;
import in.hocg.eagle.modules.com.entity.RequestLog;
import in.hocg.eagle.utils.string.JsonUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Created by hocgin on 2020/3/3.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class Logger {
    @ApiModelProperty("代码位置")
    private String mapping;
    @ApiModelProperty("来源(需使用请求参数, 如: source=eagle)")
    private String source;
    @ApiModelProperty("请求头:host")
    private String host;
    @ApiModelProperty("请求头:user-agent")
    private String userAgent;
    @ApiModelProperty("登录账号")
    private User currentUser;
    @ApiModelProperty("请求IP")
    private String clientIp;
    @ApiModelProperty("入口描述")
    private String enterRemark;
    @ApiModelProperty("请求方式")
    private String method;
    @ApiModelProperty("请求URL")
    private String uri;
    @ApiModelProperty("请求参数")
    private List<Object> args;
    @ApiModelProperty("响应")
    private Object ret;
    @ApiModelProperty("异常信息")
    private String exception;
    @ApiModelProperty("请求耗时")
    private Long totalTimeMillis;
    @ApiModelProperty("发起请求的时间")
    private LocalDateTime createdAt;
    @ApiModelProperty("线程内日志")
    private static final ThreadLocal<List<String>> LOGS_REMARK = new ThreadLocal<>();


    public static void log(String message) {
        getOrCreateLogPool().add(message);
    }

    private static List<String> getOrCreateLogPool() {
        List<String> list = LOGS_REMARK.get();
        if (Objects.isNull(list)) {
            list = Lists.newArrayList();
            LOGS_REMARK.set(list);
        }
        return list;
    }

    public void save() {
        SpringContext.getBean(LoggerService.class).handle(this);
    }

    public RequestLog asRequestLog() {
        final RequestLog entity = new RequestLog();
        entity.setArgs(JSON.toJSONString(args));
        entity.setRet(this.getRetStr());
        entity.setSource(source);
        entity.setClientIp(clientIp);
        entity.setCreatedAt(createdAt);
        entity.setMapping(mapping);
        entity.setMethod(method);
        entity.setTotalTimeMillis(totalTimeMillis);
        entity.setEnterRemark(enterRemark);
        entity.setUri(uri);
        entity.setException(exception);
        entity.setHost(host);
        entity.setUserAgent(userAgent);
        entity.setLogs(JSON.toJSONString(LOGS_REMARK));
        if (Objects.nonNull(currentUser)) {
            entity.setCreator(currentUser.getId());
        }
        return entity;
    }

    public String getRetStr() {
        String retStr;
        if ((ret instanceof InputStream) || (ret instanceof OutputStream)) {
            retStr = "File Stream";
        } else if (ret instanceof ResponseEntity && ((ResponseEntity)ret).getBody() instanceof Resource) {
            retStr = "Resource Stream";
        }else {
            retStr = JsonUtils.toJSONString(ret, true)
                .replaceAll("\n", "\n║ ");
        }
        return retStr;
    }
}
