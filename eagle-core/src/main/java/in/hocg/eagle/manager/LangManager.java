package in.hocg.eagle.manager;

import com.alibaba.fastjson.JSON;
import in.hocg.eagle.basic.env.Env;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.manager.dto.AMapDistrictResultDto;
import in.hocg.eagle.manager.dto.IpAndAddressDto;
import in.hocg.eagle.modules.com.pojo.qo.district.AMapDistrictDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    private final RedisManager redisManager;

    /**
     * 根据IP获取地址
     *
     * @param ip
     * @return
     */
    public IpAndAddressDto getAddressByIp(String ip) {
        final Optional<IpAndAddressDto> resultOpt = redisManager.getIpAndAddress(ip);
        if (resultOpt.isPresent()) {
            return resultOpt.get();
        }
        String token = Env.getConfigs().getIp138();
        String url = String.format("http://api.ip138.com/query/?ip=%s&token=%s&datatype=jsonp", ip, token);
        final IpAndAddressDto result = restTemplate.getForObject(url, IpAndAddressDto.class);
        redisManager.setIpAndAddress(ip, result);
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
