package in.hocg.eagle.modules.ums.api;

import in.hocg.eagle.modules.ums.pojo.vo.account.AccountComplexVo;

/**
 * Created by hocgin on 2020/6/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface AccountAPI {

    AccountComplexVo selectOne(Long id);
}
