package in.hocg.eagle.modules.bmw.api.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/7/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class QueryPaymentWayRo {
    @NotNull
    @ApiModelProperty(value = "接入应用编号", required = true)
    private Long appSn;
    @NotNull
    @ApiModelProperty(value = "场景码", required = true)
    private String sceneCode;
}
