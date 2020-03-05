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
    Subscription(2, "订阅");
    private final Integer code;
    private final String name;
}
