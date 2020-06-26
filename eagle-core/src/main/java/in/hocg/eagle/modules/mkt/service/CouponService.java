package in.hocg.eagle.modules.mkt.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.basic.AbstractService;
import in.hocg.eagle.modules.mkt.entity.Coupon;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponPagingQo;
import in.hocg.eagle.modules.mkt.pojo.qo.CouponSaveQo;
import in.hocg.eagle.modules.mkt.pojo.qo.GiveCouponQo;
import in.hocg.eagle.modules.mkt.pojo.vo.CouponComplexVo;

/**
 * <p>
 * 优惠券表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-03-17
 */
public interface CouponService extends AbstractService<Coupon> {

    /**
     * 分页查询
     *
     * @param qo
     * @return
     */
    IPage<CouponComplexVo> paging(CouponPagingQo qo);

    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    CouponComplexVo selectOne(Long id);

    /**
     * 赠送优惠券
     *
     * @param qo
     */
    void giveToUsers(GiveCouponQo qo);

    /**
     * 创建或更新优惠券模版
     *
     * @param qo
     */
    void saveOne(CouponSaveQo qo);
}
