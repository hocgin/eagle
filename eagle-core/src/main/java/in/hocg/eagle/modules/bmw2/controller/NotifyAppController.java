package in.hocg.eagle.modules.bmw2.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * [支付网关] 事件通知列表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-06-06
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/bmw2/notify-app")
public class NotifyAppController {

}

