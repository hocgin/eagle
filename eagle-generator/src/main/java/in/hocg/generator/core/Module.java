package in.hocg.generator.core;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


/**
 * Created by hocgin on 2020/5/29.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@RequiredArgsConstructor
public enum Module {
    TEST(Lists.newArrayList("T_"), "in.hocg.eagle.modules", "test", "eagle-core"),
    BMW(Lists.newArrayList("BMW_", "T_"), "in.hocg.eagle.modules", "bmw", "eagle-core"),
    BMW2(Lists.newArrayList("BMW_", "T_"), "in.hocg.eagle.modules", "bmw2", "eagle-core");

    /**
     * 生成的 Entity 名称会忽略前缀
     * - 优先匹配原则
     */
    private final List<String> ignoreTablePrefix;
    /**
     * 模块上的根包名
     */
    private final String packageName;
    /**
     * 模块名称
     */
    private final String moduleName;
    /**
     * 模块相对于根项目的位置
     */
    private final String relativePath;
}
