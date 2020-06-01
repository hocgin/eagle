package in.hocg.eagle.modules.bmw.mapper;

import in.hocg.eagle.modules.bmw.entity.TradeLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [支付网关] 所有交易日志表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-05-30
 */
@Mapper
public interface TradeLogMapper extends BaseMapper<TradeLog> {

}
