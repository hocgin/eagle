package in.hocg.eagle.basic.aspect.named;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Created by hocgin on 2020/2/13.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class NamedService {
    
    /**
     * 查询枚举值
     *
     * @param type
     * @param id
     * @return
     */
    public Object selectOneByTypeAndId(String type, Integer id) {
        if (Strings.isBlank(type) || Objects.isNull(id)) {
            return null;
        }
        // TODO: 等待枚举
        
        return null;
    }
}
