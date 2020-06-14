package in.hocg.web.message.event;

import in.hocg.web.constant.datadict.NotifyType;
import in.hocg.web.constant.datadict.SubjectType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by hocgin on 2020/3/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class SubscriptionEvent {
    @ApiModelProperty("触发者ID")
    private Long actorId;
    @ApiModelProperty("订阅对象ID")
    private Long subjectId;
    @ApiModelProperty("订阅类型")
    private SubjectType subjectType;
    @ApiModelProperty("订阅事件类型")
    private NotifyType notifyType;

}
