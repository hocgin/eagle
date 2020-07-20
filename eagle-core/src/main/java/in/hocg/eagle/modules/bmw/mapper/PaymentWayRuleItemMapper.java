package in.hocg.eagle.modules.bmw.mapper;

import in.hocg.eagle.modules.bmw.entity.PaymentWayRuleItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [支付网关] 支付渠道规则对应的支付渠道表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-07-18
 */
@Mapper
public interface PaymentWayRuleItemMapper extends BaseMapper<PaymentWayRuleItem> {

}
