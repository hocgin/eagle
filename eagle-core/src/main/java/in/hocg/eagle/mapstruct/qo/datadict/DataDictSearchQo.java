package in.hocg.eagle.mapstruct.qo.datadict;

import in.hocg.eagle.basic.pojo.qo.PageQo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/2/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DataDictSearchQo extends PageQo {
    private String keyword;
}
