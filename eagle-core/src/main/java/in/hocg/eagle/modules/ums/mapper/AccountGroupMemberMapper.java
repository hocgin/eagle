package in.hocg.eagle.modules.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.ums.entity.AccountGroupMember;
import in.hocg.eagle.modules.ums.pojo.qo.account.group.AccountGroupMemberPageQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [用户模块] 账号分组成员表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@Mapper
public interface AccountGroupMemberMapper extends BaseMapper<AccountGroupMember> {

    List<AccountGroupMember> selectAllByGroupId(@Param("groupId") Long groupId);

    void deleteAllByGroupId(@Param("groupId") Long groupId);

    IPage<AccountGroupMember> pagingWithComplex(@Param("qo") AccountGroupMemberPageQo qo, @Param("page") Page page);
}
