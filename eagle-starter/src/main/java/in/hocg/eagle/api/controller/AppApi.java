package in.hocg.eagle.api.controller;

import in.hocg.eagle.api.service.AppService;
import in.hocg.web.constant.AuthorizeConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RestController
@PreAuthorize(AuthorizeConstant.IS_MINI_EAGLE)
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api-mini")
public class AppApi {
    private final AppService service;

}
