package in.hocg.eagle.modules.mkt.pojo.qo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.Lists;
import in.hocg.web.jackson.LocalDateTimeDeserializer;
import in.hocg.web.pojo.qo.IdQo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by hocgin on 2020/3/29.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class GiveCouponQo extends IdQo {
    @Size(min = 1, max = 100, message = "请选择用户(1~100)")
    private List<Long> accountId = Lists.newArrayList();
    @NotNull(message = "请指定生效时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startAt;
    @NotNull(message = "请指定失效时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endAt;
}
