package in.hocg.eagle.modules.ums.service;

import in.hocg.web.AbstractService;
import in.hocg.eagle.modules.ums.pojo.qo.authority.AuthorityInsertQo;
import in.hocg.eagle.modules.ums.pojo.qo.authority.AuthoritySearchQo;
import in.hocg.eagle.modules.ums.pojo.qo.authority.AuthorityUpdateQo;
import in.hocg.eagle.modules.ums.pojo.qo.role.GrantRoleQo;
import in.hocg.eagle.modules.ums.pojo.vo.authority.AuthorityComplexAndRoleVo;
import in.hocg.eagle.modules.ums.pojo.vo.authority.AuthorityTreeNodeVo;
import in.hocg.eagle.modules.ums.entity.Authority;

import java.util.List;

/**
 * <p>
 * [用户模块] 权限表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
public interface AuthorityService extends AbstractService<Authority> {

    /**
     * 新增权限
     * - 新增权限如果具有父级别，其父级别如果是禁用状态，该权限不能为启用状态。
     *
     * @param qo
     */
    void insertOne(AuthorityInsertQo qo);

    /**
     * 更新权限
     * - 状态更新，如果父级为禁用状态，子权限不能更新为启用状态。
     * - 状态更新，如果父级更新为禁用状态，子权限均会更新为禁用状态。
     * - 父级更换，如果父级的父级发生更换，则其子级随着父级发生更换。
     *
     * @param qo
     */
    void updateOne(AuthorityUpdateQo qo);

    /**
     * 搜索权限
     *
     * @param qo
     * @return
     */
    List<Authority> search(AuthoritySearchQo qo);

    /**
     * 搜索权限
     *
     * @param qo
     * @return
     */
    List<AuthorityTreeNodeVo> tree(AuthoritySearchQo qo);

    /**
     * 删除权限
     *
     * @param id
     * @param force
     */
    void deleteById(Long id, boolean force);

    /**
     * 给角色授权权限
     *
     * @param qo
     */
    void grantRole(GrantRoleQo qo);

    /**
     * 查询权限信息和其使用的角色
     *
     * @param id
     * @return
     */
    AuthorityComplexAndRoleVo selectOne(Long id);
}
