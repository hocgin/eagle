package in.hocg.eagle.api.controller;

import in.hocg.eagle.api.pojo.SignUpQo;
import in.hocg.eagle.api.service.AppService;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.constant.AuthorizeConstant;
import in.hocg.eagle.basic.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@PreAuthorize(AuthorizeConstant.IS_MINI_EAGLE)
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api-mini")
public class AppApi {
    private final AppService service;

    @UseLogger("注册(APP)")
    @PostMapping("/account/sign-up")
    public Result<Void> signUp(@Validated @RequestBody SignUpQo qo) {
        service.signUp(qo);
        return Result.success();
    }

}
