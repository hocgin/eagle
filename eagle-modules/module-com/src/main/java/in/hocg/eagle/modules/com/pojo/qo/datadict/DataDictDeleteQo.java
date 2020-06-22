package in.hocg.eagle.modules.com.pojo.qo.datadict;

import in.hocg.web.pojo.qo.IdsQo;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by hocgin on 2020/3/1.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class DataDictDeleteQo extends IdsQo {

    @NotNull
    private Boolean force;
}
