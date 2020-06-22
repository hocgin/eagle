package in.hocg.eagle.basic.aspect;

import in.hocg.web.security.SecurityContext;
import in.hocg.eagle.modules.com.service.RequestLogService;
import in.hocg.web.SpringContext;
import in.hocg.web.aspect.logger.Logger;
import in.hocg.web.aspect.logger.LoggerService;
import in.hocg.web.security.User;
import in.hocg.web.utils.DateUtils;
import in.hocg.web.utils.string.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * Created by hocgin on 2020/2/28.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class DefaultLoggerService implements LoggerService {

    @Override
    public void handle(Logger logger) {
        printlnPrettyLogger(logger);
        try {
            SpringContext.getBean(RequestLogService.class).asyncSave(logger);
        } catch (Exception ignored) {
        }
    }

    private void printlnPrettyLogger(Logger logger) {
        StringJoiner stringJoiner = new StringJoiner("\n")
            .add("")
            .add("╔═[{}]═{}════════════════════════════════════════════════")
            .add("║ {}")
            .add("║ > {} ({})")
            .add("╠═[请求体]════════════════════════════════════════════════════════════════════════════")
            .add("║ {}")
            .add("╠═[响应体]════════════════════════════════════════════════════════════════════════════")
            .add("║ {}")
            .add("╚═[总耗时:{} ms]══════════════════════════════════════════════════════════════════════")
            .add("");
        log.info(stringJoiner.toString(),
            DateUtils.format(LocalDateTime.now(), DateUtils.SIMPLE_FORMATTER),
            getUserStringThrow(logger.getCurrentUser()),
            String.format("%s %s", logger.getMethod(), logger.getUri()),
            logger.getEnterRemark(),
            logger.getMapping(),
            JsonUtils.toJSONString(logger.getArgs(), true).replaceAll("\n", "\n║ "),
            JsonUtils.toJSONString(logger.getRet(), true).replaceAll("\n", "\n║ "),
            logger.getTotalTimeMillis());
    }

    private String getUserStringThrow(User user) {
        try {
            return getUserString(user);
        } catch (Exception e) {
            log.error("", e);
            return "数据异常";
        }
    }

    private String getUserString(User user) {
        final Optional<User> currentUser = SecurityContext.getCurrentUser();
        if (currentUser.isPresent()) {
            return String.format("[@%s(ID:%s)]", user.getUsername(), user.getId());
        }
        return "未登录";
    }
}