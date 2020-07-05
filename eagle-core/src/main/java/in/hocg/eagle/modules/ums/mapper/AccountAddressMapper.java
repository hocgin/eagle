package in.hocg.eagle.modules.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.ums.entity.AccountAddress;
import in.hocg.eagle.modules.ums.pojo.qo.account.address.AccountAddressPageQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * <p>
 * [用户模块] 收货地址表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@Mapper
public interface AccountAddressMapper extends BaseMapper<AccountAddress> {

    void updateAllUnDefaultByAccountId(@Param("accountId") Long accountId);

    IPage<AccountAddress> paging(@Param("qo") AccountAddressPageQo qo, @Param("page") Page page);

    Optional<AccountAddress> getDefaultByUserId(@Param("userId") Long userId);
}
