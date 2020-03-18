package in.hocg.eagle.modules.oms.helper;

import in.hocg.eagle.modules.oms.helper.order.GeneralOrder;
import in.hocg.eagle.modules.oms.helper.order.GeneralProduct;
import in.hocg.eagle.modules.oms.helper.order.discount.DiscountHelper;
import in.hocg.eagle.modules.oms.helper.order.discount.coupon.Coupon;
import in.hocg.eagle.basic.constant.datadict.CouponType;
import in.hocg.eagle.basic.constant.datadict.CouponUseStatus;
import in.hocg.eagle.basic.constant.datadict.CouponUseType;
import in.hocg.eagle.basic.constant.datadict.OrderSourceType;
import in.hocg.eagle.modules.oms.pojo.vo.coupon.CouponAccountComplexVo;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by hocgin on 2020/3/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
class OrderHelperTest {


    @Test
    void useDiscounts() {
        final List<GeneralProduct> products = Lists.newArrayList();
        products.add(new GeneralProduct(BigDecimal.valueOf(100L), 2)
            .setProductId(1L)
            .setProductPic("http://test.iamge")
            .setProductSkuCode("SKU001")
            .setProductSkuId(12L)
            .setProductSpecData("[]")
            .setProductName("商品A"));
        final GeneralOrder order = new GeneralOrder(products, 1, OrderSourceType.APP, LocalDateTime.now());

        final CouponAccountComplexVo couponVo = new CouponAccountComplexVo();
        couponVo.setCeiling(BigDecimal.valueOf(10000L));
        couponVo.setId(1L);
        couponVo.setUseStatus(CouponUseStatus.Unused.getCode());
        couponVo.setCouponSn("COUPON_001");
        couponVo.setTitle("优惠券A");
        couponVo.setUseType(CouponUseType.Universal.getCode());
        couponVo.setStartAt(LocalDateTime.now().minusDays(2));
        couponVo.setEndAt(LocalDateTime.now().plusDays(2));
        couponVo.setMinPoint(BigDecimal.valueOf(50));
        couponVo.setCredit(BigDecimal.valueOf(100L));
        couponVo.setCouponType(CouponType.Credit.getCode());
        final Coupon coupon = DiscountHelper.createCoupon(couponVo);

        order.use(coupon);

        System.out.println(order.toStrings());
    }
}
