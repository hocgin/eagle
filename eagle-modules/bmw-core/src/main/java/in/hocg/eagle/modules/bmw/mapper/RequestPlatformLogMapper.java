package in.hocg.eagle.modules.bmw.mapper;

import in.hocg.eagle.modules.bmw.entity.RequestPlatformLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [支付网关] 所有和第三方支付交易日志表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
@Mapper
public interface RequestPlatformLogMapper extends BaseMapper<RequestPlatformLog> {

}
