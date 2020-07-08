package in.hocg.eagle.modules.com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.com.entity.District;
import in.hocg.eagle.modules.com.pojo.qo.district.DistrictSearchQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [基础模块] 城市规划表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@Mapper
public interface DistrictMapper extends BaseMapper<District> {

    void removeAll();

    List<District> search(@Param("qo") DistrictSearchQo qo);

    List<District> selectChildrenByAdcode(@Param("adcode") String adcode);

    List<District> getProvince();

    List<District> getCity();

    List<District> getCounty();
}
