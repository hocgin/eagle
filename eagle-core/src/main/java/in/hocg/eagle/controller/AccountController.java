package in.hocg.eagle.controller;


import in.hocg.eagle.controller.qo.ExampleQo;
import in.hocg.eagle.service.vo.ExampleVo;
import in.hocg.eagle.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-02-11
 */
@Slf4j
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AccountController {
    private final AccountService accountService;
    
    @GetMapping
    public ExampleVo index(ExampleQo qo) {
        return accountService.index(qo);
    }
}

