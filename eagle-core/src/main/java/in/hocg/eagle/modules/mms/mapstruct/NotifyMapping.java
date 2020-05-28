package in.hocg.eagle.modules.mms.mapstruct;

import in.hocg.eagle.modules.mms.entity.Notify;
import in.hocg.eagle.modules.mms.pojo.dto.notify.PublishNotifyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/3/4.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface NotifyMapping {

    @Mapping(target = "title", ignore = true)
    @Mapping(target = "lastUpdater", ignore = true)
    @Mapping(target = "lastUpdatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notifyType", ignore = true)
    @Mapping(target = "subjectType", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Notify asNotify(PublishNotifyDto entity);
}
