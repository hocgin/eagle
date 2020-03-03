package in.hocg.eagle.modules.account.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.mapstruct.qo.account.AccountSearchQo;
import in.hocg.eagle.modules.account.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    IPage<Account> search(@Param("qo") AccountSearchQo qo, @Param("page") Page page);
}
