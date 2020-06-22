package in.hocg.eagle.modules.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.ums.entity.Account;
import in.hocg.eagle.modules.ums.pojo.qo.account.AccountCompleteQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.AccountSearchQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * <p>
 * [用户模块] 账号表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    Optional<Account> selectOneByUsername(@Param("username") String username);

    IPage<Account> paging(@Param("qo") AccountSearchQo qo, @Param("page") Page page);

    IPage<Account> pagingWithComplete(@Param("qo") AccountCompleteQo qo, @Param("page") Page page);

}
