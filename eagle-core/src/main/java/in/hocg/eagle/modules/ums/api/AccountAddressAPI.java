package in.hocg.eagle.modules.ums.api;

import in.hocg.eagle.modules.ums.pojo.vo.account.address.AccountAddressComplexVo;

import java.util.Optional;

/**
 * Created by hocgin on 2020/7/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface AccountAddressAPI {
    Optional<AccountAddressComplexVo> getDefaultByUserId(Long userId);
}
