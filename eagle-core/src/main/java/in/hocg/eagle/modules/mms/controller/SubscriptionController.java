package in.hocg.eagle.modules.mms.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * [消息模块] 订阅列表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-03-04
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/notify/subscription")
public class SubscriptionController {

}

