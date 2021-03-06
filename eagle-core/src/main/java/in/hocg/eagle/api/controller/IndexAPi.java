package in.hocg.eagle.api.controller;

import in.hocg.eagle.api.service.AppService;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping("/api-mini")
public class IndexAPi {
    private final AppService service;

    @UseLogger("获取用户头像")
    @ResponseBody
    @GetMapping("/account/{username}.avatar")
    public ResponseEntity getAvatarUrl(@PathVariable String username) {
        return Result.preview(service.getAvatarUrlByUsername(username).orElse(null));
    }

}
