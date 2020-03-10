package in.hocg.eagle.modules.notify.message;

import in.hocg.eagle.mapstruct.qo.notify.PublishSubscriptionDto;
import in.hocg.eagle.modules.notify.message.event.SubscriptionEvent;
import in.hocg.eagle.modules.notify.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by hocgin on 2020/3/8.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class NotifyEventListener {
    private final NotificationService notificationService;

    @EventListener(classes = SubscriptionEvent.class)
    public void subscriptionEvent(SubscriptionEvent event) throws Throwable {
        final PublishSubscriptionDto dto = new PublishSubscriptionDto();
        dto.setActorId(event.getActorId());
        dto.setNotifyType(event.getNotifyType().getCode());
        dto.setSubjectId(event.getSubjectId());
        dto.setSubjectType(event.getSubjectType().getCode());
        notificationService.publishSubscription(dto);
    }
}
