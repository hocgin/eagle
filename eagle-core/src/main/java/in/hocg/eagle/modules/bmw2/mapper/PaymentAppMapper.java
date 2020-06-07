package in.hocg.eagle.modules.bmw2.mapper;

import in.hocg.eagle.modules.bmw2.entity.PaymentApp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [支付网关] 接入方表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
@Mapper
public interface PaymentAppMapper extends BaseMapper<PaymentApp> {

}
