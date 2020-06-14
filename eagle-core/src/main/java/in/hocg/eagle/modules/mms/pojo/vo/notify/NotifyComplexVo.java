package in.hocg.eagle.modules.mms.pojo.vo.notify;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.web.jackson.LocalDateTimeSerializer;
import in.hocg.eagle.modules.ums.pojo.vo.account.AccountComplexVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/3/4.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class NotifyComplexVo {
    @ApiModelProperty("通知ID")
    private Long notifyId;
    @ApiModelProperty("通知类型")
    private Integer notifyType;
    @ApiModelProperty("订阅对象类型")
    private Integer subjectType;
    @ApiModelProperty("订阅对象ID")
    private Long subjectId;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("接收者ID")
    private Long receiverId;
    private AccountComplexVo receiver;
    @ApiModelProperty("触发者ID")
    private Long actorId;
    private AccountComplexVo actor;

    @ApiModelProperty("读取消息的时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime readAt;
    @ApiModelProperty("发送消息的时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

}
