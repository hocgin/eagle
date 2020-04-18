package in.hocg.eagle.modules.oms.pojo.qo.cart;

import in.hocg.eagle.basic.pojo.qo.PageQo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by hocgin on 2020/4/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CartItemPagingQo extends PageQo {
    private Integer accountId;
}
