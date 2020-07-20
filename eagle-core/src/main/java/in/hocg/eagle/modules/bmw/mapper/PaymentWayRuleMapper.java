package in.hocg.eagle.modules.bmw.mapper;

import in.hocg.eagle.modules.bmw.entity.PaymentWayRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.bmw.entity.PaymentWayRuleItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [支付网关] 支付渠道规则表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-07-18
 */
@Mapper
public interface PaymentWayRuleMapper extends BaseMapper<PaymentWayRule> {

    List<PaymentWayRuleItem> selectListByAppIdAndSceneCode(@Param("appId") Long appId, @Param("sceneCode") String sceneCode);
}
