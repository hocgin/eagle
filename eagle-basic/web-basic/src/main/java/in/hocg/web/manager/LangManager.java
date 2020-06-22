package in.hocg.web.manager;

import com.alibaba.fastjson.JSON;
import in.hocg.web.env.Env;
import in.hocg.web.exception.ServiceException;
import in.hocg.web.manager.dto.AMapDistrictDto;
import in.hocg.web.manager.dto.AMapDistrictResultDto;
import in.hocg.web.manager.dto.IpAndAddressDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

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
        String token = Env.getConfigs().getIp138();
        String url = String.format("http://api.ip138.com/query/?ip=%s&token=%s&datatype=jsonp", ip, token);
        final IpAndAddressDto result = restTemplate.getForObject(url, IpAndAddressDto.class);
        return result;
    }

    /**
     * 获取高德的城市列表信息
     *
     * @return
     */
    public List<AMapDistrictDto> getDistrict() {
        String token = Env.getConfigs().getAmap();
        String subdistrict = "3";
        String url = String.format("https://restapi.amap.com/v3/config/district?key=%s&subdistrict=%s", token, subdistrict);
        String resultText = restTemplate.getForObject(url, String.class);
        final AMapDistrictResultDto result = JSON.parseObject(resultText, AMapDistrictResultDto.class);
        if (Objects.isNull(result) || !result.isOk()) {
            throw ServiceException.wrap("请求高德数据失败");
        }
        return result.getDistricts();
    }
}