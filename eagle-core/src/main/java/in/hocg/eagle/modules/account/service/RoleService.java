package in.hocg.eagle.modules.account.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.mapstruct.qo.GrantAuthorityQo;
import in.hocg.eagle.mapstruct.qo.RolePostQo;
import in.hocg.eagle.mapstruct.qo.RolePutQo;
import in.hocg.eagle.mapstruct.qo.RoleSearchQo;
import in.hocg.eagle.mapstruct.vo.RoleComplexVo;
import in.hocg.eagle.mapstruct.vo.RoleSearchVo;
import in.hocg.eagle.modules.account.entity.Authority;
import in.hocg.eagle.modules.account.entity.Role;

import java.util.List;

/**
 * <p>
 * [用户模块] 角色表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
public interface RoleService extends AbstractService<Role> {
    
    /**
     * 新增角色
     *
     * @param qo
     */
    void insertOne(RolePostQo qo);
    
    /**
     * 修改角色
     *
     * @param qo
     */
    void updateOne(RolePutQo qo);
    
    /**
     * 分页查询
     *
     * @param qo
     * @return
     */
    IPage<RoleSearchVo> search(RoleSearchQo qo);
    
    /**
     * 给角色授权
     *
     * @param qo
     */
    void grantAuthority(GrantAuthorityQo qo);
    
    /**
     * 删除角色
     *
     * @param id
     */
    void deleteOne(Integer id);
    
    /**
     * 查询角色详情
     *
     * @param id
     * @return
     */
    RoleComplexVo selectOne(Integer id);
    
    List<Authority> selectListAuthorityByIds(List<Integer> roleIds);
}
