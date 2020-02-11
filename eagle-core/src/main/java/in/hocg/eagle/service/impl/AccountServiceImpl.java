package in.hocg.eagle.service.impl;

import in.hocg.eagle.controller.qo.ExampleQo;
import in.hocg.eagle.service.vo.ExampleVo;
import in.hocg.eagle.entity.Account;
import in.hocg.eagle.mapper.AccountMapper;
import in.hocg.eagle.service.AccountService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Service
public class AccountServiceImpl extends AbstractServiceImpl<AccountMapper, Account> implements AccountService {
    
    @Override
    public ExampleVo index(ExampleQo qo) {
        return new ExampleVo();
    }
}
