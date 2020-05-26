package in.hocg.eagle.modules.com.pojo.qo.changelog;

import in.hocg.eagle.basic.constant.datadict.ChangeLogRefType;
import in.hocg.eagle.basic.pojo.qo.PageQo;
import in.hocg.eagle.basic.valid.EnumRange;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/4/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChangeLogPagingQo extends PageQo {
    @NotNull(message = "业务类型不能为空")
    @EnumRange(enumClass = ChangeLogRefType.class, message = "业务类型错误")
    @ApiModelProperty("业务类型")
    private Integer refType;

    @NotNull(message = "业务ID不能为空")
    @ApiModelProperty("业务ID")
    private Long refId;

}
