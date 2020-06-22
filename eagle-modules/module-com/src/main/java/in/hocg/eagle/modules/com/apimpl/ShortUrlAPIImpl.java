package in.hocg.eagle.modules.com.apimpl;

import in.hocg.eagle.modules.com.api.ShortUrlAPI;
import in.hocg.eagle.modules.com.entity.ShortUrl;
import in.hocg.eagle.modules.com.service.ShortUrlService;
import in.hocg.web.constant.datadict.Enabled;
import in.hocg.web.utils.LangUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by hocgin on 2020/6/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ShortUrlAPIImpl implements ShortUrlAPI {
    private final ShortUrlService shortUrlService;

    @Override
    public String selectOneByCode(String code) {
        final Optional<ShortUrl> shortUrlOpt = shortUrlService.selectOneByCode(code);
        if (!shortUrlOpt.isPresent() || !LangUtils.equals(Enabled.On.getCode(), shortUrlOpt.get().getEnabled())) {
            return null;
        }
        return shortUrlOpt.get().getOriginalUrl();
    }
}
