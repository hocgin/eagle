package in.hocg.eagle.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by hocgin on 2020/1/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class LangUtils {
    
    /**
     * 获取如果为 NULL，则返回默认值
     *
     * @param v
     * @param def
     * @param <T>
     * @return
     */
    public <T> T getOrDefault(T v, T def) {
        if (Objects.isNull(v)) {
            return def;
        }
        return v;
    }
    
    /**
     * 如果传入的值不为 NULL，则当作入参执行后续函数
     *
     * @param v
     * @param func
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Optional<V> callIfNotNull(K v, Function<K, V> func) {
        if (Objects.nonNull(v)) {
            return Optional.of(func.apply(v));
        }
        return Optional.empty();
    }
    
    /**
     * List 转为 Map
     *
     * @param values
     * @param keyFunction
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> toMap(Iterable<V> values, Function<? super V, K> keyFunction) {
        Map<K, V> result = Maps.newHashMap();
        for (V val : values) {
            K key = keyFunction.apply(val);
            result.put(key, val);
        }
        return result;
    }
    
    /**
     * 数组 转为 Map
     *
     * @param values
     * @param keyFunction
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> toMap(V[] values, Function<? super V, K> keyFunction) {
        Map<K, V> result = Maps.newHashMap();
        for (V val : values) {
            K key = keyFunction.apply(val);
            result.put(key, val);
        }
        return result;
    }
    
    /**
     * 提取 List 项的值
     *
     * @param values
     * @param keyFunction
     * @param <V>
     * @param <R>
     * @return
     */
    public static <V, R> List<R> toList(Iterable<V> values, Function<? super V, R> keyFunction) {
        List<R> result = Lists.newArrayList();
        for (V val : values) {
            result.add(keyFunction.apply(val));
        }
        return result;
    }
    
    /**
     * Integer 的比较
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean equals(Integer s1, Integer s2) {
        if (s1 == null || s2 == null) {
            return Objects.equals(s1, s2);
        }
        
        return s1.compareTo(s2) == 0;
    }
}
