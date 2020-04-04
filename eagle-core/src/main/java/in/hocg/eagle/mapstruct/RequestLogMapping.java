package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.com.entity.RequestLog;
import in.hocg.eagle.modules.com.pojo.vo.requestlog.RequestLogComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/4/4.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface RequestLogMapping {
    @Mapping(target = "creatorName", ignore = true)
    RequestLogComplexVo asRequestLogComplexVo(RequestLog entity);
}
