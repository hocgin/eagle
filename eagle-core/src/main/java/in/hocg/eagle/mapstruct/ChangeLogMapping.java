package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.com.entity.ChangeLog;
import in.hocg.eagle.modules.com.pojo.vo.changelog.ChangeLogComplexVo;
import in.hocg.eagle.utils.compare.ChangeLogDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/4/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface ChangeLogMapping {

    @Mapping(target = "logSn", ignore = true)
    @Mapping(target = "id", ignore = true)
    ChangeLog asChangeLog(ChangeLogDto dto);

    ChangeLogComplexVo asChangeLogComplexVo(ChangeLog entity);
}
