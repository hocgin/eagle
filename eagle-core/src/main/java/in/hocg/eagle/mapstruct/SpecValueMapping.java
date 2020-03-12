package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.shop.entity.SpecValue;
import in.hocg.eagle.modules.shop.pojo.vo.spec.SpecValueComplexVo;
import org.mapstruct.Mapper;

/**
 * Created by hocgin on 2020/3/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface SpecValueMapping {
    SpecValueComplexVo asSpecValueComplex(SpecValue entity);
}
