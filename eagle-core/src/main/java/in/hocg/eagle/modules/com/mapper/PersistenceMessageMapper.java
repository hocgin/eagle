package in.hocg.eagle.modules.com.mapper;

import in.hocg.eagle.modules.com.entity.PersistenceMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [基础模块] 持久化消息表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-07-20
 */
@Mapper
public interface PersistenceMessageMapper extends BaseMapper<PersistenceMessage> {

}
