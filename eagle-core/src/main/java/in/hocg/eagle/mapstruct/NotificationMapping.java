package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.ums.pojo.vo.account.AccountComplexVo;
import in.hocg.eagle.modules.mms.pojo.vo.notify.NotifyComplexVo;
import in.hocg.eagle.modules.mms.entity.Notification;
import in.hocg.eagle.modules.mms.entity.Notify;
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
    @Mapping(target = "content", source = "notify.content")
    NotifyComplexVo asSearchNotifyVo(Notification notification, Notify notify);

    default NotifyComplexVo asSearchNotifyVo(Notification notification,
                                             Notify notify,
                                             AccountComplexVo receiver,
                                             AccountComplexVo actor) {
        final NotifyComplexVo result = asSearchNotifyVo(notification, notify);
        result.setReceiver(receiver);
        result.setActor(actor);
        return result;
    }

}
