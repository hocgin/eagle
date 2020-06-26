package in.hocg.eagle.modules.ums.pojo.qo.account;

import in.hocg.eagle.basic.pojo.ro.PageRo;
import lombok.Data;

/**
 * Created by hocgin on 2020/3/3.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class AccountSearchQo extends PageRo {
    private String keyword;
}
