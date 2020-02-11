package in.hocg.eagle.utils;

import com.google.common.base.Strings;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hocgin
 * @date 2019/7/19
 */
public class TextBlock {
    /**
     * 普通占位符
     */
    private static final Pattern PLACEHOLDER = Pattern.compile("\\{.*?}");
    /**
     * SQL 占位符
     */
    public static final Pattern SQL_PLACEHOLDER = Pattern.compile("\\?");
    
    public static String format(String format) {
        return format;
    }
    
    public static String format(String format, Object arg1) {
        if (Objects.isNull(arg1) || !arg1.getClass().isArray()) {
            return arrayFormat(format, new Object[]{arg1});
        }
        
        return arrayFormat(format, (Object[]) arg1);
    }
    
    public static String format(String format, Object arg1, Object... args) {
        ArrayList<Object> allArg = new ArrayList<>(args.length + 1);
        allArg.add(arg1);
        allArg.addAll(Arrays.asList(args));
        return arrayFormat(format, allArg.toArray());
    }
    
    /**
     * 字符串占位符 {} 替换
     * 例如: "我是 {}", "HOCGIN"  ==> "我是 HOCGIN"
     * 例如: "我是 {name}", "HOCGIN"  ==> "我是 HOCGIN"
     *
     * @param messagePattern
     * @param args
     * @return
     */
    private static String arrayFormat(@NonNull final String messagePattern,
                                      @NonNull Object[] args) {
        return arrayFormat(messagePattern, args, PLACEHOLDER);
    }
    
    
    public static String arrayFormat(@NonNull final String messagePattern,
                                     @NonNull Object[] args,
                                     @NonNull Pattern placeholder) {
        StringBuffer sb = new StringBuffer();
        if (!Strings.isNullOrEmpty(messagePattern)) {
            Matcher matcher = placeholder.matcher(messagePattern);
            int i = 0;
            while (matcher.find()) {
                if (args.length < (i + 1)) {
                    break;
                }
                matcher.appendReplacement(sb, Objects.toString(args[i++]));
            }
            matcher.appendTail(sb);
        }
        return sb.toString();
    }
    
}
