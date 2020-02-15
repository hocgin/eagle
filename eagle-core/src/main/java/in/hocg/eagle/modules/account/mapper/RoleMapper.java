package in.hocg.eagle.modules.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.mapstruct.qo.RoleSearchQo;
import in.hocg.eagle.mapstruct.vo.RoleSearchVo;
import in.hocg.eagle.modules.account.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [用户模块] 角色表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    
    /**
     * 分页查询
     *
     * @param qo
     * @param page
     * @return
     */
    IPage<RoleSearchVo> search(@Param("qo") RoleSearchQo qo, Page page);
}
