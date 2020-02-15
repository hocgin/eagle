package in.hocg.eagle.modules.account.mapper;

import in.hocg.eagle.modules.account.entity.AuthorityAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [用户模块] 权限-账号表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Mapper
public interface AuthorityAccountMapper extends BaseMapper<AuthorityAccount> {
    
    Integer selectListByAuthorityRegexTreePath(@Param("regexTreePath") String regexTreePath);
}
