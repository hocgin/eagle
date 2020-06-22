package in.hocg.eagle.modules.ums.api;

import in.hocg.eagle.modules.ums.api.vo.Account;
import in.hocg.eagle.modules.ums.api.vo.AccountComplexVo;

/**
 * Created by hocgin on 2020/6/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface AccountAPI {
    AccountComplexVo selectOneComplex(Long id);

    Account getById(Long id);
}
