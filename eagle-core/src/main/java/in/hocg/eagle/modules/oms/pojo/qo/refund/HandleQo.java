package in.hocg.eagle.modules.oms.pojo.qo.refund;

import in.hocg.eagle.basic.pojo.ro.IdRo;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/3/28.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class HandleQo extends IdRo {
    @NotNull(message = "处理失败")
    private Boolean isPass;
    private String handleRemark;
}
