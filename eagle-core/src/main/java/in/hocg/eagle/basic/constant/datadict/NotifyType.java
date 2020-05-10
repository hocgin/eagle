package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/3/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("通知类型")
@RequiredArgsConstructor
public enum NotifyType implements IntEnum {
    PrivateLetter(0, "私信", "收到一条私信"),
    Announcement(1, "公告", "收到一条公告"),
    // > 100 都是订阅范围
    Subscription(100, "订阅", "收到一条订阅通知"),
    /**
     * @see in.hocg.eagle.modules.crm.entity.Comment
     */
    SubscriptionComment(111, "订阅::评论", "收到一条评价");
    private final Integer code;
    private final String name;
    private final String message;
}
