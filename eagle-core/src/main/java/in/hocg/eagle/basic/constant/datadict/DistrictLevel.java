package in.hocg.eagle.basic.constant.datadict;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/8/11
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@ApiModel("城市规划级别")
@RequiredArgsConstructor
public enum DistrictLevel implements DataDictEnum {
    Country(0, "国家"),
    Province(1, "省份"),
    City(2, "城市"),
    District(3, "区域"),
    Street(4, "街道");
    private final Integer code;
    private final String name;

    public final static String KEY = "DistrictLevel";

    /**
     * 常量
     **/
    public final static Integer CountryCode = 0;
    public final static Integer ProvinceCode = 1;
    public final static Integer CityCode = 2;
    public final static Integer DistrictCode = 3;
    public final static Integer StreetCode = 4;
}
