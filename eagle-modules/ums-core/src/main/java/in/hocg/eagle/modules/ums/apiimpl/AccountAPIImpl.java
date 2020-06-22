package in.hocg.eagle.modules.ums.apiimpl;

import in.hocg.eagle.modules.ums.api.AccountAPI;
import in.hocg.eagle.modules.ums.api.vo.AccountComplexVo;
import in.hocg.eagle.modules.ums.service.AccountService;
import in.hocg.web.utils.LangUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Created by hocgin on 2020/6/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AccountAPIImpl implements AccountAPI {
    private final AccountService service;

    @Override
    public AccountComplexVo selectOneComplex(Long id) {
        return LangUtils.copyProperties(service.selectOneComplex(id), new AccountComplexVo());
    }
}
