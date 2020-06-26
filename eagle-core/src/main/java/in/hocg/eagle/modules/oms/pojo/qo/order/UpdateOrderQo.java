package in.hocg.eagle.modules.oms.pojo.qo.order;

import in.hocg.eagle.basic.pojo.ro.IdRo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Created by hocgin on 2020/3/24.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateOrderQo extends IdRo {
    @ApiModelProperty("收货人姓名")
    private String receiverName;
    @ApiModelProperty("收货人电话")
    private String receiverPhone;
    @ApiModelProperty("收货人邮编")
    private String receiverPostCode;
    @ApiModelProperty("省份/直辖市")
    private String receiverProvince;
    @ApiModelProperty("城市")
    private String receiverCity;
    @ApiModelProperty("区")
    private String receiverRegion;
    @ApiModelProperty("详细地址")
    private String receiverDetailAddress;
    @ApiModelProperty("管理员优惠")
    private BigDecimal discountAmount;

}
