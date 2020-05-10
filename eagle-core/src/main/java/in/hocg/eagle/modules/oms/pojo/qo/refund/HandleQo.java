package in.hocg.eagle.modules.oms.pojo.qo.refund;

import in.hocg.eagle.basic.pojo.qo.IdQo;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/3/28.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class HandleQo extends IdQo {
    @NotNull(message = "处理失败")
    private Boolean isPass;
    private String handleRemark;
}
