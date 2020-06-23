package in.hocg.eagle.modules.ums.api;

import in.hocg.eagle.modules.ums.api.vo.AccountComplexVo;

/**
 * Created by hocgin on 2020/6/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface AccountAPI {
    AccountComplexVo selectOne(Long id);
}
