package in.hocg.eagle.modules.base.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 文件引用表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-02-19
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/file")
public class FileController {

}

