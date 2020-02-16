package in.hocg.eagle.modules.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.account.entity.RoleAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    
    /**
     * 查找正在使用该角色的数量
     *
     * @param roleId
     * @return
     */
    Integer countByRoleId(@Param("roleId") Integer roleId);
    
    /**
     * 查找该账号正在使用该角色的数量
     *
     * @param accountId
     * @param roleId
     * @return
     */
    Integer countByAccountIdAndRoleId(@Param("accountId") Integer accountId, @Param("roleId") Integer roleId);
}
