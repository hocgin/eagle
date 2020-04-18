package in.hocg.eagle.modules.com.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.mapstruct.SystemSettingsMapping;
import in.hocg.eagle.modules.com.entity.SystemSettings;
import in.hocg.eagle.modules.com.mapper.SystemSettingsMapper;
import in.hocg.eagle.modules.com.pojo.qo.systemsettings.SystemSettingsPagingQo;
import in.hocg.eagle.modules.com.pojo.qo.systemsettings.SystemSettingsSaveQo;
import in.hocg.eagle.modules.com.pojo.vo.systemsettings.SystemSettingsComplexVo;
import in.hocg.eagle.modules.com.service.SystemSettingsService;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * [基础模块] 系统配置表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-18
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SystemSettingsServiceImpl extends AbstractServiceImpl<SystemSettingsMapper, SystemSettings> implements SystemSettingsService {

    private final SystemSettingsMapping mapping;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<String> selectOneWithString(String configCode) {
        final Optional<SystemSettings> systemSettingsOpt = this.selectOneByConfigCode(configCode);
        return systemSettingsOpt.map(SystemSettings::getConfigCode);
    }

    public Optional<SystemSettings> selectOneByConfigCode(String configCode) {
        return lambdaQuery().eq(SystemSettings::getConfigCode, configCode).oneOpt();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<SystemSettingsComplexVo> pagingWithComplex(SystemSettingsPagingQo qo) {
        return baseMapper.pagingWithComplex(qo, qo.page())
            .convert(this::convertComplex);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(SystemSettingsSaveQo qo) {
        final LocalDateTime createdAt = qo.getCreatedAt();
        final Long userId = qo.getUserId();
        final Long id = qo.getId();
        SystemSettings entity = mapping.asSystemSettings(qo);
        if (Objects.isNull(id)) {
            entity.setCreatedAt(createdAt);
            entity.setCreator(userId);
        } else {
            entity.setLastUpdatedAt(createdAt);
            entity.setLastUpdater(userId);
        }
        validInsertOrUpdate(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SystemSettingsComplexVo selectOne(Long id) {
        final SystemSettings entity = getById(id);
        ValidUtils.notNull(entity, "数据不存在");
        return convertComplex(entity);
    }

    @Override
    public void validEntity(SystemSettings entity) {
        super.validEntity(entity);

        final String configCode = entity.getConfigCode();
        if (Objects.nonNull(configCode)) {
            ValidUtils.isFalse(hasConfigCodeIgnoreId(configCode, entity.getId()), "配置码已存在");
        }
    }

    private boolean hasConfigCodeIgnoreId(String code, Long ignoreId) {
        return baseMapper.countAndConfigCodeIgnoreId(code, ignoreId) > 0;
    }

    private SystemSettingsComplexVo convertComplex(SystemSettings entity) {
        return mapping.asSystemSettingsComplexVo(entity);
    }
}
