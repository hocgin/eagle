package in.hocg.eagle.modules.pms.pojo.qo.category;

import in.hocg.web.pojo.qo.BaseQo;
import lombok.Data;

/**
 * Created by hocgin on 2020/3/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class ProductCategorySearchQo extends BaseQo {
    private Long parentId;

}
