package in.hocg.eagle.modules.com.service.impl;

import in.hocg.eagle.basic.datastruct.tree.Tree;
import in.hocg.eagle.basic.mybatis.tree.TreeServiceImpl;
import in.hocg.eagle.manager.LangManager;
import in.hocg.eagle.modules.com.mapstruct.DistrictMapping;
import in.hocg.eagle.modules.com.entity.District;
import in.hocg.eagle.modules.com.mapper.DistrictMapper;
import in.hocg.eagle.modules.com.pojo.qo.district.AMapDistrictDto;
import in.hocg.eagle.modules.com.pojo.qo.district.DistrictSearchQo;
import in.hocg.eagle.modules.com.pojo.vo.district.DistrictComplexVo;
import in.hocg.eagle.modules.com.pojo.vo.district.DistrictTreeVo;
import in.hocg.eagle.modules.com.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * [基础模块] 城市规划表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class DistrictServiceImpl extends TreeServiceImpl<DistrictMapper, District>
    implements DistrictService {
    private final DistrictMapping mapping;
    private final LangManager langManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importByAMapUrl() {
        this.removeAll();
        insertList(null, langManager.getDistrict());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DistrictTreeVo> tree() {
        final DistrictSearchQo newQo = new DistrictSearchQo();
        List<District> all = baseMapper.search(newQo);
        return Tree.getChild(null, all.parallelStream()
            .map(mapping::asDistrictTreeVo)
            .collect(Collectors.toList()));
    }

    @Override
    public List<DistrictComplexVo> selectChildrenByAdcode(String adCode) {
        return baseMapper.selectChildrenByAdcode(adCode)
            .stream().map(this::convertComplex)
            .collect(Collectors.toList());
    }


    private DistrictComplexVo convertComplex(District entity) {
        return mapping.asDistrictComplexVo(entity);
    }

    private void insertList(Long parentId, List<AMapDistrictDto> dtos) {
        if (Objects.isNull(dtos)) {
            return;
        }
        for (AMapDistrictDto dto : dtos) {
            final District entity = new District();
            entity.setParentId(parentId);
            entity.setCityCode(dto.getCitycode());
            entity.setAdCode(dto.getAdcode());
            entity.setTitle(dto.getName());
            entity.setLat(dto.getLat());
            entity.setLng(dto.setLng());
            validInsertOrUpdate(entity);
            insertList(entity.getId(), dto.getDistricts());
        }
    }

    private Optional<District> selectOneByAdCode(String adCode) {
        return lambdaQuery().eq(District::getAdCode, adCode).oneOpt();
    }

    private void removeAll() {
        baseMapper.removeAll();
    }

}
