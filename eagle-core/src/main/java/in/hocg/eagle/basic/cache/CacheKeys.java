package in.hocg.eagle.basic.cache;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hocgin on 2020/5/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public final class CacheKeys {
    public final static String DEMO = "DEMO";

    @Getter
    private final static Map<String, Long> CACHE_KEY_MAP = new HashMap<String, Long>() {{
        put(CacheKeys.DEMO, 2L);
    }};
}
