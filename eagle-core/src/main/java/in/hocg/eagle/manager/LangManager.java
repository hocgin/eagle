package in.hocg.eagle.manager;

import in.hocg.eagle.manager.dto.IpAndAddressDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by hocgin on 2020/4/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class LangManager {
    private final RestTemplate restTemplate;

    /**
     * 根据IP获取地址
     *
     * @param ip
     * @return
     */
    public IpAndAddressDto getAddressByIp(String ip) {
        String token = "34579df219c0eadf6c9f02f610c8169b";
        String url = String.format("http://api.ip138.com/query/?ip=%s&token=%s&datatype=jsonp", ip, token);
        return restTemplate.getForObject(url, IpAndAddressDto.class);
    }


}
