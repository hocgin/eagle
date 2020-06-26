package in.hocg.eagle.manager.lang.dto;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * Created by hocgin on 2018/9/22.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class IpAndAddressDto {

    /**
     * ret : ok
     * ip : 106.122.172.244
     * data : ["中国","福建","厦门","电信","",""]
     */
    private String ret;
    private String ip;
    private List<String> data;

    public boolean isOk() {
        return "ok".equalsIgnoreCase(ret);
    }

    /**
     * 获取地址信息
     * 中国,福建,厦门
     *
     * @return
     */
    public Optional<String> getAddress() {
        return get(0, 3);
    }

    /**
     * 获取国家名称
     *
     * @return
     */
    public Optional<String> getNation() {
        if (CollectionUtils.isEmpty(data)) {
            return Optional.empty();
        }

        return Optional.ofNullable(data.get(0));
    }

    /**
     * 获取省名称
     *
     * @return
     */
    public Optional<String> getProvince() {
        if (CollectionUtils.isEmpty(data) && data.size() >= 1) {
            return Optional.empty();
        }

        return Optional.ofNullable(data.get(1));
    }

    /**
     * 获取市名称
     *
     * @return
     */
    public Optional<String> getCity() {
        if (CollectionUtils.isEmpty(data) && data.size() >= 2) {
            return Optional.empty();
        }

        return Optional.ofNullable(data.get(2));
    }

    /**
     * 获取运营商
     *
     * @return
     */
    public Optional<String> getOperator() {
        return get(3, 4);
    }

    /**
     * 获取邮编
     *
     * @return
     */
    public Optional<String> getZipCode() {
        return get(4, 5);
    }

    /**
     * 获取区号
     * 0592
     *
     * @return
     */
    public Optional<String> getCityCode() {
        return get(5, 6);
    }

    private Optional<String> get(int fromIndex, int toIndex) {
        if (!isOk()) {
            return Optional.empty();
        }
        try {
            return Optional.of(String.join(",", data.subList(fromIndex, toIndex)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
