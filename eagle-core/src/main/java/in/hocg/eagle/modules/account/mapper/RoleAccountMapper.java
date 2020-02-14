package in.hocg.eagle.modules.account.mapper;

import in.hocg.eagle.modules.account.entity.RoleAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [用户模块] 角色-账号表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Mapper
public interface RoleAccountMapper extends BaseMapper<RoleAccount> {

}
