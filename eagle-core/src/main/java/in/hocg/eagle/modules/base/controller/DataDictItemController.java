package in.hocg.eagle.modules.base.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * [基础模块] 数据字典项表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-02-14
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/data-dict-item")
public class DataDictItemController {

}

