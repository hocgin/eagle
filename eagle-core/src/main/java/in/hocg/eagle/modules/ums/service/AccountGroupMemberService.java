package in.hocg.eagle.modules.ums.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractService;
import in.hocg.eagle.modules.ums.entity.AccountGroupMember;
import in.hocg.eagle.modules.ums.pojo.qo.account.group.AccountGroupMemberDeleteQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.group.AccountGroupMemberPageQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.group.JoinMemberQo;
import in.hocg.eagle.modules.ums.pojo.vo.account.group.AccountGroupMemberComplexVo;

/**
 * <p>
 * [用户模块] 账号分组成员表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
public interface AccountGroupMemberService extends AbstractService<AccountGroupMember> {

    void deleteAllByGroupId(Long groupId);

    IPage<AccountGroupMemberComplexVo> pagingWithComplex(AccountGroupMemberPageQo qo);

    void deleteListWithMember(AccountGroupMemberDeleteQo qo);

    void join(JoinMemberQo qo);
}
