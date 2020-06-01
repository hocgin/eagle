package in.hocg.eagle.modules.bmw.mapper;

import in.hocg.eagle.modules.bmw.entity.PaymentRefund;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [支付网关] 退款记录表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-06-01
 */
@Mapper
public interface PaymentRefundMapper extends BaseMapper<PaymentRefund> {

}
