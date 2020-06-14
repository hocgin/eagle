package in.hocg.web.utils.compare;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by hocgin on 2020/4/10.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@ApiModel
public class ChangeLogDto {
    @ApiModelProperty("日志类型:[订单]")
    private Integer refType;
    @ApiModelProperty("业务ID:[订单ID]")
    private Long refId;
    @ApiModelProperty("变更类型:[新增/修改/删除]")
    private Integer changeType;
    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;
    @ApiModelProperty("创建人")
    private Long creator;
    @ApiModelProperty("字段变更记录")
    private List<FieldChangeDto> change = Lists.newArrayList();
}
