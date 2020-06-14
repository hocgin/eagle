package in.hocg.eagle.modules.com.service;

import in.hocg.web.AbstractService;
import in.hocg.eagle.modules.com.entity.District;
import in.hocg.eagle.modules.com.pojo.vo.district.DistrictComplexVo;
import in.hocg.eagle.modules.com.pojo.vo.district.DistrictTreeVo;

import java.util.List;

/**
 * <p>
 * [基础模块] 城市规划表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
public interface DistrictService extends AbstractService<District> {

    void importByAMapUrl();

    List<DistrictTreeVo> tree();

    List<DistrictComplexVo> selectChildrenByAdcode(String adCode);
}
