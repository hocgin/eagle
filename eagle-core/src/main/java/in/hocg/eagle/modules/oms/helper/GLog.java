package in.hocg.eagle.modules.oms.helper;

import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Created by hocgin on 2019/10/22.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@UtilityClass
public class GLog {
    ThreadLocal<List<String>> localLines = new ThreadLocal<>();
    private static final int MIN_STACK_OFFSET = 3;
    private static final int METHOD_OFFSET = 0;

    public static void addLog(String line) {
        getOrCreate().add(process(line));
    }

    public static String string() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (String s : getOrCreate()) {
            stringJoiner.add(s);
        }
        return stringJoiner.toString();
    }

    public static void print() {
        log.info(string());
    }

    public static void clear() {
        getOrCreate().clear();
    }

    /**
     * ====================================
     *
     * ====================================
     */
    private List<String> getOrCreate() {
        List<String> lines = localLines.get();
        if (Objects.isNull(lines) || lines.isEmpty()) {
            localLines.set(Lists.newArrayList());
        }
        return localLines.get();
    }

    private String process(String line) {
        // 位置坐标
        List<String> pos = getPos(1);
        String posStr = "";
        if (!pos.isEmpty()) {
            posStr = pos.get(0);
        }

        // 时间
        String nowString = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        return String.format("%s\t\t\t\t\t\t日志来自于:[%s][%s]", line, posStr, nowString);
    }

    private int _getStackOffset(StackTraceElement[] trace) {
        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(GLog.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

    private List<String> getPos(int methodCount) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int stackOffset = _getStackOffset(stackTrace) + METHOD_OFFSET;

        if (methodCount + stackOffset > stackTrace.length) {
            methodCount = stackTrace.length - stackOffset - 1;
        }

        List<String> lines = Lists.newArrayList();
        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= stackTrace.length) {
                continue;
            }
            StackTraceElement item = stackTrace[stackIndex];
            String line = String.format("%s.%s (%s:%d)", item.getClassName(), item.getMethodName(), item.getFileName(), item.getLineNumber());
            lines.add(line);
        }

        return lines;
    }
}
