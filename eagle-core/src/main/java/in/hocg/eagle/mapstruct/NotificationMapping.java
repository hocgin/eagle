package in.hocg.eagle.mapstruct;

import in.hocg.eagle.mapstruct.vo.account.IdAccountComplexVo;
import in.hocg.eagle.mapstruct.vo.notify.SearchNotifyVo;
import in.hocg.eagle.modules.notify.entity.Notification;
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
public interface NotificationMapping {
    @Mapping(target = "receiver", ignore = true)
    @Mapping(target = "actor", ignore = true)
    @Mapping(target = "notifyId", source = "notification.notifyId")
    @Mapping(target = "readAt", source = "notification.readAt")
    @Mapping(target = "receiverId", source = "notification.receiverId")
    @Mapping(target = "createdAt", source = "notify.createdAt")
    @Mapping(target = "notifyType", source = "notify.notifyType")
    @Mapping(target = "subjectType", source = "notify.subjectType")
    @Mapping(target = "subjectId", source = "notify.subjectId")
    @Mapping(target = "actorId", source = "notify.actorId")
    SearchNotifyVo asSearchNotifyVo(Notification notification, Notify notify);

    default SearchNotifyVo asSearchNotifyVo(Notification notification,
                                            Notify notify,
                                            IdAccountComplexVo receiver,
                                            IdAccountComplexVo actor) {
        final SearchNotifyVo result = asSearchNotifyVo(notification, notify);
        result.setReceiver(receiver);
        result.setActor(actor);
        return result;
    }
}
