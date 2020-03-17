package in.hocg.eagle.modules.com.pojo.qo.datadict.item;

import in.hocg.eagle.basic.pojo.qo.IdQo;
import lombok.Data;

/**
 * Created by hocgin on 2020/3/2.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class DataDictItemUpdateQo extends IdQo {
    private String title;
    private String code;
    private String remark;
    private Integer sort;
    private Long dictId;
    private Integer enabled;
}
