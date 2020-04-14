package in.hocg.eagle.modules;


import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import in.hocg.eagle.basic.aspect.logger.UseLogger;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.modules.com.entity.ShortUrl;
import in.hocg.eagle.modules.com.service.ShortUrlService;
import in.hocg.eagle.utils.LangUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Optional;

/**
 * <p>
 * [基础模块] 短链接表 前端控制器
 * </p>
 *
 * @author hocgin
 * @since 2020-04-04
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@RequestMapping
public class IndexController {
    private final ShortUrlService shortUrlService;

    @GetMapping
    @ResponseBody
    public String index() {
        return "worked";
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
        final Optional<ShortUrl> shortUrlOpt = shortUrlService.selectOneByCode(code);
        if (!shortUrlOpt.isPresent() || !LangUtils.equals(Enabled.On.getCode(), shortUrlOpt.get().getEnabled())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(shortUrlOpt.get().getOriginalUrl()))
            .build();
    }
}

