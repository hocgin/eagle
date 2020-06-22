package in.hocg.eagle.modules.crm.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * [评论模块] 评论对象表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-03-08
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/comment/comment-target")
public class CommentTargetController {

}

