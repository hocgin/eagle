package in.hocg.eagle.modules.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.oms.entity.OrderRefundApply;
import in.hocg.eagle.modules.oms.pojo.qo.refund.OrderRefundApplyPagingQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单退货申请 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-03-16
 */
@Mapper
public interface OrderRefundApplyMapper extends BaseMapper<OrderRefundApply> {

    IPage<OrderRefundApply> paging(@Param("qo") OrderRefundApplyPagingQo qo, @Param("page") Page page);
}
