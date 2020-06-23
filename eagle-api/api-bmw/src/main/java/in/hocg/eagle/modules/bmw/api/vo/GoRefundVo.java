package in.hocg.eagle.modules.bmw.api.vo;

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
public class GoRefundVo {
    @ApiModelProperty("退款单号(网关)")
    private String refundSn;
}
