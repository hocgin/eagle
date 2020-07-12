package in.hocg.eagle.modules.bmw.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import in.hocg.eagle.basic.ext.jackson.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by hocgin on 2020/7/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class WaitPaymentTradeVo {
    @ApiModelProperty("交易金额")
    private BigDecimal totalFee;
    @ApiModelProperty("创建时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    @ApiModelProperty("过期时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime expireAt;
}
