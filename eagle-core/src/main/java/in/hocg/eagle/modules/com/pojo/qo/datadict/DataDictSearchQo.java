package in.hocg.eagle.modules.com.pojo.qo.datadict;

import in.hocg.eagle.basic.pojo.ro.PageRo;
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
public class DataDictSearchQo extends PageRo {
    private String keyword;
}
