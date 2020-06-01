package in.hocg.eagle.modules.bmw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.bmw.entity.PaymentTransaction;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [支付网关] 交易流水表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-05-30
 */
@Mapper
public interface PaymentTransactionMapper extends BaseMapper<PaymentTransaction> {
}
