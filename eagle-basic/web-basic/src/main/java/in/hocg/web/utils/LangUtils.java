package in.hocg.web.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import in.hocg.web.utils.clazz.ClassUtils;
import in.hocg.web.utils.clazz.PropertyNamer;
import in.hocg.web.utils.lambda.SFunction;
import in.hocg.web.utils.lambda.SerializedLambda;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.net.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public <K, V> Optional<V> callIfNotNull(K v, Function<K, V> func) {
        if (Objects.nonNull(v)) {
            return Optional.ofNullable(func.apply(v));
        }
        return Optional.empty();
    }

    public <K, V> List<V> covert(Collection<K> v, Function<K, V> func) {
        return v.parallelStream().map(func).collect(Collectors.toList());
    }

    /**
     * 如果传入的值不为 NULL，则当作入参执行后续函数
     *
     * @param v
     * @param consumer
     * @param>
     */
    public <T> void setIfNotNull(T v, Consumer<T> consumer) {
        if (Objects.nonNull(v)) {
            consumer.accept(v);
        }
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
    public <K, V> Map<K, V> toMap(Iterable<V> values, Function<? super V, K> keyFunction) {
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
    public <K, V> Map<K, V> toMap(V[] values, Function<? super V, K> keyFunction) {
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
    public <V, R> List<R> toList(Iterable<V> values, Function<? super V, R> keyFunction) {
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
    public boolean equals(Integer s1, Integer s2) {
        if (s1 == null || s2 == null) {
            return Objects.equals(s1, s2);
        }

        return s1.compareTo(s2) == 0;
    }

    public boolean equals(Long s1, Long s2) {
        if (s1 == null || s2 == null) {
            return Objects.equals(s1, s2);
        }

        return s1.compareTo(s2) == 0;
    }

    public boolean equals(String s1, String s2) {
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
    public String toString(Object object) {
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
     * 移除 all 中的 sub
     * all - sub
     *
     * @param all
     * @param sub
     * @param biFunction
     * @param <R>
     * @param <T>
     * @return
     */
    public <R, T> List<R> removeIfExits(Collection<R> all, Collection<T> sub, BiFunction<R, T, Boolean> biFunction) {
        List<R> result = Lists.newArrayList(all);
        if (result.isEmpty()) {
            return result;
        }
        final Iterator<R> iterator = result.iterator();
        while (iterator.hasNext()) {
            final R r = iterator.next();
            for (T t : sub) {
                if (biFunction.apply(r, t)) {
                    iterator.remove();
                }
            }
        }
        return result;
    }

    /**
     * 获取两个 List 的交集
     *
     * @param l1
     * @param l2
     * @param biFunction
     * @param <R>
     * @param <T>
     * @return
     */
    public <R, T> List<R> getMixed(Collection<R> l1, Collection<T> l2, BiFunction<R, T, Boolean> biFunction) {
        List<R> result = Lists.newArrayList();
        for (R r : l1) {
            for (T t : l2) {
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
    public String getSpace(int spaceCount) {
        return String.join("", Collections.nCopies(spaceCount, " "));
    }

    public String md5(byte[] bytes) {
        return Hashing.md5().newHasher().putBytes(bytes).hash().toString();
    }

    public <T> Optional<T> parse(String text, Class<T> clazz) {
        if (Strings.isBlank(text)) {
            return Optional.empty();
        }

        if (ClassUtils.isBaseType(clazz)) {
            return Optional.of((T) text);
        }
        return Optional.of(JSON.parseObject(text, clazz));
    }

    /**
     * 获取当前所在的 IPv4
     *
     * @return
     */
    public List<String> getIpv4Addresses() {
        List<NetworkInterface> is = Collections.emptyList();
        try {
            is = Collections.list(NetworkInterface.getNetworkInterfaces()).stream()
                .filter(i -> {
                    try {
                        return !i.isLoopback();
                    } catch (SocketException ignored) {
                    }
                    return false;
                })
                .collect(Collectors.toList());
        } catch (SocketException ignored) {
        }

        return is.stream().flatMap(i -> {
            final Enumeration<InetAddress> addresses = i.getInetAddresses();
            final Stream.Builder<String> builder = Stream.builder();
            while (addresses.hasMoreElements()) {
                final InetAddress ip = addresses.nextElement();
                if (!ip.isLoopbackAddress()) {
                    if (ip.getHostAddress().equalsIgnoreCase("127.0.0.1")) {
                        continue;
                    }
                    if (ip instanceof Inet6Address) {
                        continue;
                    }
                    if (ip instanceof Inet4Address) {
                        builder.add(ip.getHostAddress());
                    }
                }
            }
            return builder.build();
        }).collect(Collectors.toList());
    }

    /**
     * 分组调用
     * - 将大批量数据，分组后，在整合
     *
     * @param allIds
     * @param queryFunction
     * @param len
     * @param <R>
     * @param <P>
     * @return
     */
    public <R, P> List<R> groupCallback(Collection<P> allIds,
                                        Function<Collection<P>, Collection<R>> queryFunction, int len) {
        if (CollectionUtils.isEmpty(allIds)) {
            return Collections.emptyList();
        }
        List<P> all = Lists.newArrayList(allIds);
        List<R> result = Lists.newArrayList();

        int startIndex = 0;
        final int maxLength = all.size();

        while (startIndex < maxLength) {
            int toIndex = Math.min((startIndex + len), maxLength);

            final List<P> ids = all.subList(startIndex, toIndex);
            result.addAll(queryFunction.apply(ids));
            startIndex = toIndex;
        }
        return result;
    }


    /**
     * 拷贝属性（先都调用这边，方便更换）
     *
     * @param source
     * @param target
     */
    public static <T extends Object> T copyProperties(Object source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public <T extends Object> T copyProperties(Object source, T target,
                                               @NonNull List<String> ignore) {
        BeanUtils.copyProperties(source, target, ignore.toArray(new String[0]));
        return target;
    }

    public <T extends Object> T copyProperties(Object source, T target, SFunction<T, ?>... ignoreFunc) {
        final List<String> ignoreFieldNames = Arrays.stream(ignoreFunc).map(func -> PropertyNamer.methodToProperty(SerializedLambda.resolve(func).getImplMethodName()))
            .collect(Collectors.toList());
        return copyProperties(source, target, ignoreFieldNames);
    }

    public Integer getIntRandomCode(int i) {
        return 10086;
    }
}
