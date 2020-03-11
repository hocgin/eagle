package in.hocg.eagle.mapstruct.qo.notify;

import in.hocg.eagle.basic.pojo.qo.PageQo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/3/4.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchNotifyPagingQo extends PageQo {
    @ApiModelProperty("接收者")
    private Long receiverId;
    @ApiModelProperty("通知类型")
    private Integer notifyType;

}
