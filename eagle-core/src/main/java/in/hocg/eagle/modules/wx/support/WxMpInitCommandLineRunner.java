package in.hocg.eagle.modules.wx.support;

import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.modules.wx.entity.WxMpConfig;
import in.hocg.eagle.modules.wx.manager.WxMpManager;
import in.hocg.eagle.modules.wx.mapstruct.WxMpMapping;
import in.hocg.eagle.modules.wx.service.WxMpConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hocgin on 2020/4/26.
 * email: hocgin@gmail.com
 * 多微信配置初始化
 *
 * @author hocgin
 */
@Slf4j
@Order
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class WxMpInitCommandLineRunner implements CommandLineRunner {
    private final WxMpConfigService wxMpConfigService;
    private final WxMpManager wxMpManager;
    private final WxMpMapping wxMpMapping;

    @Override
    public void run(String... args) {
        List<WxMpConfig> wxMpConfigs = wxMpConfigService.selectListByEnabled(Enabled.On.getCode());
        if (wxMpConfigs.isEmpty()) {
            return;
        }
        wxMpManager.getWxMpService().setMultiConfigStorages(wxMpConfigs.parallelStream()
            .map(wxMpMapping::asWxMpConfigStorage)
            .collect(Collectors.toMap(WxMpConfigStorage::getAppId, a -> a, (o, n) -> o)));
    }
}
