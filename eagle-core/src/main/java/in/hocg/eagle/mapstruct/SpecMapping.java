package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.shop.entity.Spec;
import in.hocg.eagle.modules.shop.pojo.vo.spec.SpecComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/3/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface SpecMapping {
    @Mapping(target = "values", ignore = true)
    SpecComplexVo asSpecComplexVo(Spec entity);
}
