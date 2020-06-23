package in.hocg.eagle.modules.bmw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.bmw.entity.PaymentTrade;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [支付网关] 交易流水表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
@Mapper
public interface PaymentTradeMapper extends BaseMapper<PaymentTrade> {

}
