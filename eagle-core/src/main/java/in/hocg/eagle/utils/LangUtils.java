package in.hocg.eagle.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.BiFunction;
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

    public static boolean equals(Long s1, Long s2) {
        if (s1 == null || s2 == null) {
            return Objects.equals(s1, s2);
        }

        return s1.compareTo(s2) == 0;
    }

    public static boolean equals(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return Objects.equals(s1, s2);
        }

        return s1.equals(s2);
    }

    /**
     * toString
     *
     * @param object
     * @return
     */
    public static String toString(Object object) {
        if (Objects.isNull(object)) {
            return null;
        }
        if (object instanceof Integer) {
            return Integer.toString((Integer) object);
        } else if (object instanceof String) {
            return ((String) object);
        } else {
            return object.toString();
        }
    }

    /**
     * 两个集合之间过滤
     *
     * @param all
     * @param sub
     * @param biFunction
     * @param <R>
     * @param <T>
     * @return
     */
    public <R, T> List<R> removeIfExits(List<R> all, List<T> sub, BiFunction<R, T, Boolean> biFunction) {
        List<R> result = Lists.newArrayList();
        for (R r : all) {
            for (T t : sub) {
                if (biFunction.apply(r, t)) {
                    result.add(r);
                }
            }
        }
        return result;
    }

    /**
     * 获取空格数量
     *
     * @param spaceCount
     * @return
     */
    public static String getSpace(int spaceCount) {
        return String.join("", Collections.nCopies(spaceCount, " "));
    }

    public static String md5(byte[] bytes) {
        return Hashing.md5().newHasher().putBytes(bytes).hash().toString();
    }
}
