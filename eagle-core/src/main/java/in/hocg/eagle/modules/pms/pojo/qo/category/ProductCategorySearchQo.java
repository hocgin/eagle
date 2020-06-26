package in.hocg.eagle.modules.pms.pojo.qo.category;

import in.hocg.eagle.basic.pojo.ro.BaseRo;
import lombok.Data;

/**
 * Created by hocgin on 2020/3/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class ProductCategorySearchQo extends BaseRo {
    private Long parentId;

}
