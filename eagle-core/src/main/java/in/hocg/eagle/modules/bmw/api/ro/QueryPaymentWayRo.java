package in.hocg.eagle.modules.bmw.api.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

/**
 * Created by hocgin on 2020/7/7.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class QueryPaymentWayRo {
    @NonNull
    @ApiModelProperty(value = "接入应用编号", required = true)
    private Long appSn;
}
