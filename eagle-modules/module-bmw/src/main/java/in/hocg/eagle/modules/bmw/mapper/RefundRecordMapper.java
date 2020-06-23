package in.hocg.eagle.modules.bmw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.bmw.entity.RefundRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [支付网关] 退款记录表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
@Mapper
public interface RefundRecordMapper extends BaseMapper<RefundRecord> {

}
