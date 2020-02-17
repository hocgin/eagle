package in.hocg.eagle.basic.aspect.named;

import in.hocg.eagle.basic.SpringContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

/**
 * Created by hocgin on 2020/2/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RequiredArgsConstructor
public enum NamedType {
    DataDict(SpringContext.getBean(NamedService.class)::selectOneByDataDict);
    
    @Getter
    private final BiFunction<Object, String[], Object> function;
}