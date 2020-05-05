package in.hocg.eagle.modules.wx.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 微信用户表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-04-25
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api/wx/user")
public class WxUserController {

}

