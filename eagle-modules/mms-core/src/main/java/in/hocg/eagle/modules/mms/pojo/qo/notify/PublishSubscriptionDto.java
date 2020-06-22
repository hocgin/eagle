package in.hocg.eagle.modules.mms.pojo.qo.notify;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hocgin on 2020/3/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class PublishSubscriptionDto {
    @ApiModelProperty("触发者ID")
    private Long actorId;
    @ApiModelProperty("订阅对象ID")
    private Long subjectId;
    @ApiModelProperty("订阅类型")
    private Integer subjectType;
    @ApiModelProperty("订阅事件类型")
    private Integer notifyType;

}
