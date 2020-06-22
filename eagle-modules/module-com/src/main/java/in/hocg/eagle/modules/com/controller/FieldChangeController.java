package in.hocg.eagle.modules.com.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * [基础模块] 业务日志-字段变更记录表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-04-11
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/field-change")
public class FieldChangeController {

}

