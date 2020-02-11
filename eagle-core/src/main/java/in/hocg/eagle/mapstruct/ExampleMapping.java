package in.hocg.eagle.mapstruct;


import in.hocg.eagle.mapstruct.bo.ExampleBo;
import in.hocg.eagle.mapstruct.qo.ExampleQo;
import org.mapstruct.Mapper;

/**
 * Created by hocgin on 2020/2/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface ExampleMapping {
    
    ExampleBo toExampleBo(ExampleQo qo);
}
