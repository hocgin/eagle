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
    PrivateLetter(0, "私信"),
    Announcement(1, "公告"),
    // > 100 都是订阅范围
    Subscription(100, "订阅"),
    /**
     * @see in.hocg.eagle.modules.comment.entity.Comment
     */
    SubscriptionComment(111, "订阅::评论");
    private final Integer code;
    private final String name;
}
