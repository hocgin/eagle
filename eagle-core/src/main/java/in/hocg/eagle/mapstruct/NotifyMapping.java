package in.hocg.eagle.mapstruct;

import in.hocg.eagle.mapstruct.dto.PublishNotifyDto;
import in.hocg.eagle.modules.notify.entity.Notify;
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
