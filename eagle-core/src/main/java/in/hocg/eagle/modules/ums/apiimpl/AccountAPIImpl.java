package in.hocg.eagle.modules.ums.apiimpl;

import in.hocg.eagle.modules.ums.api.AccountAPI;
import in.hocg.eagle.modules.ums.pojo.vo.account.AccountComplexVo;
import in.hocg.eagle.modules.ums.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Created by hocgin on 2020/6/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AccountAPIImpl implements AccountAPI {
    private final AccountService service;

    @Override
    public AccountComplexVo selectOne(Long id) {
        return service.selectOne(id);
    }
}
