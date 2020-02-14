package in.hocg.eagle.modules.account.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.mapstruct.vo.IdAccountComplexVo;
import in.hocg.eagle.modules.account.entity.Account;

import java.io.Serializable;

/**
 * <p>
 * [用户模块] 账号表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
public interface AccountService extends AbstractService<Account> {
    
    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    IdAccountComplexVo selectOneComplex(Serializable id);
}
