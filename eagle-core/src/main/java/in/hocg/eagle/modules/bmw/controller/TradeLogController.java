package in.hocg.eagle.modules.bmw.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * [支付网关] 所有交易日志表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-05-30
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/bmw/trade-log")
public class TradeLogController {

}

