package in.hocg.eagle.modules.lang;


import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import in.hocg.boot.logging.autoconfiguration.core.UseLogger;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.ext.cache.CacheKeys;
import in.hocg.eagle.basic.result.Result;
import in.hocg.eagle.modules.com.entity.ShortUrl;
import in.hocg.eagle.modules.lang.pojo.SendSmsCodeRo;
import in.hocg.eagle.modules.lang.service.IndexService;
import in.hocg.eagle.modules.ums.pojo.qo.account.AccountSignUpQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.ChangePasswordUseSmsCodeQo;
import in.hocg.eagle.modules.ums.pojo.qo.account.ResetPasswordUseMailRo;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
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

    @UseLogger("退出登录")
    @PostMapping("/api/logout")
    @ResponseBody
    public Result<String> logout() {
        return Result.success();
    }

    @UseLogger("注册账号")
    @PostMapping("/api/sign-up")
    @ResponseBody
    public Result<String> signUp(@Validated @RequestBody AccountSignUpQo qo) {
        return Result.success(service.signUp(qo));
    }

    @UseLogger("更改密码:验证码")
    @PostMapping("/api/change-password:sms-code")
    @ResponseBody
    public Result<String> changePasswordUseSmsCode(@Validated @RequestBody ChangePasswordUseSmsCodeQo qo) {
        ValidUtils.isTrue(LangUtils.equals(qo.getPassword(), qo.getConfirmPassword()), "确认密码不一致");
        return Result.success(service.changePasswordUsePhone(qo));
    }

    @UseLogger("重置密码:邮件")
    @PostMapping("/api/reset-password:mail")
    @ResponseBody
   public Result<String> sendResetPasswordUseMail(@Validated @RequestBody ResetPasswordUseMailRo ro) {
        service.sendResetPasswordUseMail(ro);
        return Result.success();
    }

    @UseLogger("发送验证码")
    @PostMapping("/api/sms-code")
    @ResponseBody
    public Result<Void> sendSmsCode(@Validated @RequestBody SendSmsCodeRo qo) {
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

