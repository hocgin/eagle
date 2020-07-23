package in.hocg.eagle.modules.com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.com.entity.PersistenceMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

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

    List<PersistenceMessage> selectListByUnCompleteAndAfter(@Param("startAt") LocalDateTime startAt,
                                                            @Param("limit") Integer limit);
}
