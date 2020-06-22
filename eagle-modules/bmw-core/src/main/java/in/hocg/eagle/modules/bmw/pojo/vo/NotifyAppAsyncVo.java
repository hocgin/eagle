package in.hocg.eagle.modules.bmw.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class NotifyAppAsyncVo<T> extends QueryAsyncVo<T> {
    @ApiModelProperty("通知ID")
    private Long notifyId;
    @ApiModelProperty("通知类型")
    private Integer notifyType;
    @ApiModelProperty("通知时间")
    private LocalDateTime notifyAt;
}
