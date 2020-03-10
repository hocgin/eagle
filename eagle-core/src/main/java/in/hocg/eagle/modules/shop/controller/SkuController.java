package in.hocg.eagle.modules.shop.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * [Shop模块] 商品SKU表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-03-10
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/shop/sku")
public class SkuController {

}

