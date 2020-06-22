package in.hocg.eagle.modules.ums.pojo.qo.account;

import in.hocg.web.pojo.qo.PageQo;
import lombok.Data;

/**
 * Created by hocgin on 2020/3/3.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class AccountSearchQo extends PageQo {
    private String keyword;
}
