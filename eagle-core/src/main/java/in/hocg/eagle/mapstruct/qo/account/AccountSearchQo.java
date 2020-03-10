package in.hocg.eagle.mapstruct.qo.account;

import in.hocg.eagle.basic.pojo.qo.PageQo;
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
