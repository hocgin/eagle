package in.hocg.eagle.modules.com.pojo.qo.district;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hocgin on 2020/4/18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class AMapDistrictDto {
    @ApiModelProperty("城市编码")
    private String citycode;
    @ApiModelProperty("区域编码")
    private String adcode;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("中心经纬度")
    private String center;
    @ApiModelProperty("子节点")
    private List<AMapDistrictDto> districts;

    public String getCitycode() {
        return "[]".equals(citycode) ? null : citycode;
    }

    public List<BigDecimal> getLngLat() {
        try {
            final String[] lnglat = center.split(",");
            return Lists.newArrayList(new BigDecimal(lnglat[0]), new BigDecimal(lnglat[1]));
        } catch (Exception e) {
            return Lists.newArrayList(null, null);
        }
    }

    public BigDecimal getLat() {
        return getLngLat().get(1);
    }

    public BigDecimal setLng() {
        return getLngLat().get(0);
    }
}
