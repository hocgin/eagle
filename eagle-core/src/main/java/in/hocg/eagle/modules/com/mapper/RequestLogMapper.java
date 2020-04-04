package in.hocg.eagle.modules.com.mapper;

import in.hocg.eagle.modules.com.entity.RequestLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [基础模块] 请求日志表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-04-04
 */
@Mapper
public interface RequestLogMapper extends BaseMapper<RequestLog> {

}
