package in.hocg.eagle.modules.account.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.account.entity.RoleAuthority;

import java.util.List;

/**
 * <p>
 * [用户模块] 角色-权限表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
public interface RoleAuthorityService extends AbstractService<RoleAuthority> {
    
    /**
     * 检查 authority 是否正在被角色使用
     *
     * @param regexTreePath
     * @return
     */
    boolean isUsedAuthority(String regexTreePath);
    
    /**
     * 给角色授权
     *
     * @param id
     * @param authorities
     */
    void grantAuthority(Integer roleId, List<Integer> authorities);
    
    /**
     * 删除角色相关的权限绑定
     * @param roleId
     */
    void deleteByRoleId(Integer roleId);
}
