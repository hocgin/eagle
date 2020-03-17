package in.hocg.eagle.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.api.AppMapping;
import in.hocg.eagle.api.pojo.ProductPagingApiQo;
import in.hocg.eagle.api.pojo.SelfOrderPagingApiQo;
import in.hocg.eagle.api.pojo.SignUpApiQo;
import in.hocg.eagle.basic.constant.datadict.ProductPublishStatus;
import in.hocg.eagle.basic.pojo.qo.IdQo;
import in.hocg.eagle.modules.oms.pojo.qo.order.OrderPagingQo;
import in.hocg.eagle.modules.oms.pojo.vo.order.OrderComplexVo;
import in.hocg.eagle.modules.oms.service.OrderService;
import in.hocg.eagle.modules.shop.pojo.qo.ProductPagingQo;
import in.hocg.eagle.modules.shop.pojo.vo.product.ProductComplexVo;
import in.hocg.eagle.modules.shop.service.ProductService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AppService {
    private final OrderService orderService;
    private final AppMapping appMapping;
    private final ProductService productService;


    public void signUp(SignUpApiQo qo) {

    }

    public OrderComplexVo getSelfOrderById(IdQo qo) {
        final Long orderId = qo.getId();
        final Long userId = qo.getUserId();
        final OrderComplexVo result = orderService.selectOne(orderId);
        ValidUtils.isTrue(LangUtils.equals(result.getAccountId(), userId), "非订单拥有人");
        return result;
    }

    public IPage<OrderComplexVo> pagingSelfOrder(SelfOrderPagingApiQo qo) {
        OrderPagingQo newQo = appMapping.asOrderPagingQo(qo);
        newQo.setAccountId(qo.getUserId());
        return orderService.paging(newQo);
    }

    public IPage<ProductComplexVo> pagingProduct(ProductPagingApiQo qo) {
        ProductPagingQo newQo = appMapping.asProductPagingQo(qo);
        newQo.setPublishStatus(ProductPublishStatus.Shelves.getCode());
        return productService.paging(newQo);
    }

    public ProductComplexVo getProductById(IdQo qo) {
        final ProductComplexVo result = productService.selectOne(qo.getId());
        ValidUtils.isTrue(LangUtils.equals(result.getPublishStatus(), ProductPublishStatus.SoldOut.getCode()), "商品已下架");
        return result;
    }
}
