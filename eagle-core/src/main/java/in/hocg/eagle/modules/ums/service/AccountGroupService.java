package in.hocg.eagle.modules.ums.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.modules.ums.entity.AccountGroup;
import in.hocg.eagle.modules.ums.pojo.qo.account.group.*;
import in.hocg.eagle.modules.ums.pojo.vo.account.group.AccountGroupComplexVo;
import in.hocg.eagle.modules.ums.pojo.vo.account.group.AccountGroupMemberComplexVo;

/**
 * <p>
 * [用户模块] 账号分组表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
public interface AccountGroupService extends AbstractService<AccountGroup> {

    void saveOne(AccountGroupSaveQo qo);

    IPage<AccountGroupComplexVo> pagingWithComplex(AccountGroupPageQo qo);

    void join(JoinMemberQo qo);

    IPage<AccountGroupMemberComplexVo> pagingWithMember(AccountGroupMemberPageQo qo);

    void deleteListWithMember(AccountGroupMemberDeleteQo qo);

    void deleteOne(IdRo qo);

    AccountGroupComplexVo selectOne(Long id);
}
