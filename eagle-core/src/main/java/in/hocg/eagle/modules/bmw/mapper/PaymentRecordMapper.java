package in.hocg.eagle.modules.bmw.mapper;

import in.hocg.eagle.modules.bmw.entity.PaymentRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [支付网关] 支付记录表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
@Mapper
public interface PaymentRecordMapper extends BaseMapper<PaymentRecord> {

}
