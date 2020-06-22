package in.hocg.eagle.modules.com.pojo.vo.changelog;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by hocgin on 2020/4/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class FieldChangeComplexVo {
    @ApiModelProperty("字段名")
    private String fieldName;
    @ApiModelProperty("字段备注")
    private String fieldRemark;
    @ApiModelProperty("变更描述")
    private String changeRemark;
    @ApiModelProperty("变更前")
    private String beforeValue;
    @ApiModelProperty("变更后")
    private String afterValue;
}
