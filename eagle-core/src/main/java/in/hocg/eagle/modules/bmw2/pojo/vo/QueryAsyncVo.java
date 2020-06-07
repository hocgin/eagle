package in.hocg.eagle.modules.bmw2.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by hocgin on 2020/6/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
@ApiModel("查询信息")
public class QueryAsyncVo<T> {
    @ApiModelProperty("平台")
    private Integer platformType;
    @ApiModelProperty("状态同步")
    private T data;
}
