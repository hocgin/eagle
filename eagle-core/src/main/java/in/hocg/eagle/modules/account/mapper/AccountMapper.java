package in.hocg.eagle.modules.account.mapper;

import in.hocg.eagle.modules.account.entity.Account;
import com.baomidou.mybatisplus.mapper.BaseMapper;
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
}
