package in.hocg.eagle.modules.ums.apiimpl;

import in.hocg.eagle.modules.ums.api.AccountAddressAPI;
import in.hocg.eagle.modules.ums.pojo.vo.account.address.AccountAddressComplexVo;
import in.hocg.eagle.modules.ums.service.AccountAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by hocgin on 2020/7/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AccountAddressAPIImpl implements AccountAddressAPI {
    private final AccountAddressService service;

    @Override
    public Optional<AccountAddressComplexVo> getDefaultByUserId(Long userId) {
        return service.getDefaultByUserId(userId);
    }
}
