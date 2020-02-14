package in.hocg.eagle.basic.aspect.named;

import in.hocg.eagle.modules.base.service.DataDictService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Created by hocgin on 2020/2/13.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class NamedService {
    
    private final DataDictService service;
    
    /**
     * 查询枚举值
     *
     * @param type
     * @param item
     * @return
     */
    public Object selectOneByTypeAndId(String type, String item) {
        if (Strings.isBlank(type) || Strings.isBlank(item)) {
            return null;
        }
        return service.selectOneByDictIdAndCode(type, item).orElse(null);
    }
}
