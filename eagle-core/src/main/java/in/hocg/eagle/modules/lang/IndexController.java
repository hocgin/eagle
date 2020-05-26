package in.hocg.eagle.modules.lang;


import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.cache.CacheKeys;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.com.entity.ShortUrl;
import in.hocg.eagle.modules.lang.pojo.SendSmsCode;
import in.hocg.eagle.modules.lang.service.IndexService;
import in.hocg.eagle.modules.ums.pojo.qo.account.AccountSignUpQo;
import in.hocg.eagle.utils.LangUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * <p>
 * [基础模块] 短链接表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-04-04
 */
@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping
public class IndexController {
    private final IndexService service;

    @UseLogger("注册账号")
    @PostMapping("/sign-up")
    @ResponseBody
    public Result<Void> signUp(@Validated @RequestBody AccountSignUpQo qo) {
        service.signUp(qo);
        return Result.success();
    }

    @UseLogger("发送验证码")
    @PostMapping("/sms-code")
    @ResponseBody
    public Result<Void> sendSmsCode(@Validated @RequestBody SendSmsCode qo) {
        service.sendSmsCode(qo);
        return Result.success();
    }

    @Cacheable(value = CacheKeys.DEMO)
    @GetMapping
    @ResponseBody
    public String index() {
        final String now = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        log.info("请求时间" + now);
        return "worked " + now;
    }

    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        CaptchaUtil.out(5, request, response);
        CaptchaUtil.out(130, 48, 5, request, response);
        GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 4);
        CaptchaUtil.out(gifCaptcha, request, response);
    }

    @UseLogger("转发 - 短链接")
    @GetMapping("/s/{code}")
    public ResponseEntity<Void> shortUrl(@PathVariable String code) {
        final Optional<ShortUrl> shortUrlOpt = service.selectOneByCode(code);
        if (!shortUrlOpt.isPresent() || !LangUtils.equals(Enabled.On.getCode(), shortUrlOpt.get().getEnabled())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(shortUrlOpt.get().getOriginalUrl()))
            .build();
    }
}
