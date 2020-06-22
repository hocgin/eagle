package in.hocg.eagle.modules.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.ums.entity.AccountGroup;
import in.hocg.eagle.modules.ums.pojo.qo.account.group.AccountGroupPageQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [用户模块] 账号分组表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@Mapper
public interface AccountGroupMapper extends BaseMapper<AccountGroup> {

    IPage<AccountGroup> pagingWithComplex(@Param("qo") AccountGroupPageQo qo, @Param("page") Page page);
}
