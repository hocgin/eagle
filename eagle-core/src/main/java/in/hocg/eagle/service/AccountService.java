package in.hocg.eagle.service;

import in.hocg.eagle.controller.qo.ExampleQo;
import in.hocg.eagle.service.vo.ExampleVo;
import in.hocg.eagle.entity.Account;
import in.hocg.eagle.basic.AbstractService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
public interface AccountService extends AbstractService<Account> {
    
    ExampleVo index(ExampleQo qo);
}
