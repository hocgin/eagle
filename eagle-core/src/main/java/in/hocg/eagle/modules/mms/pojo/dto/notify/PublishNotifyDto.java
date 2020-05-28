package in.hocg.eagle.modules.mms.pojo.dto.notify;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.constant.datadict.NotifyType;
import in.hocg.eagle.basic.constant.datadict.SubjectType;
import in.hocg.eagle.basic.pojo.qo.BaseQo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by hocgin on 2020/3/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PublishNotifyDto extends BaseQo {

    @ApiModelProperty("通知类型")
    private NotifyType notifyType;

    @ApiModelProperty("消息文本?")
    private String content;

    @ApiModelProperty("订阅对象类型?")
    private SubjectType subjectType;

    @ApiModelProperty("订阅对象ID?")
    private Long subjectId;

    @ApiModelProperty("触发者ID")
    private Long actorId;

    @ApiModelProperty("接收人IDs")
    private List<Long> receivers = Lists.newArrayList();
}
