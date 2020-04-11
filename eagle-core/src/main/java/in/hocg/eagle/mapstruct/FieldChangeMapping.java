package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.com.entity.FieldChange;
import in.hocg.eagle.utils.compare.FieldChangeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/4/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface FieldChangeMapping {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "changeLog", ignore = true)
    FieldChange asFieldChange(FieldChangeDto dto);
}
