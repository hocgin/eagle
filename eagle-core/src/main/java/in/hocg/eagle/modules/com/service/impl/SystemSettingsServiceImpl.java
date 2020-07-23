package in.hocg.eagle.modules.com.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.constant.GlobalConstant;
import in.hocg.eagle.basic.constant.config.ConfigEnum;
import in.hocg.eagle.basic.constant.config.ConfigService;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.modules.com.entity.SystemSettings;
import in.hocg.eagle.modules.com.mapper.SystemSettingsMapper;
import in.hocg.eagle.modules.com.mapstruct.SystemSettingsMapping;
import in.hocg.eagle.modules.com.pojo.dto.SystemSettingInitDto;
import in.hocg.eagle.modules.com.pojo.qo.systemsettings.SystemSettingsPagingQo;
import in.hocg.eagle.modules.com.pojo.qo.systemsettings.SystemSettingsSaveQo;
import in.hocg.eagle.modules.com.pojo.vo.systemsettings.SystemSettingsComplexVo;
import in.hocg.eagle.modules.com.service.SystemSettingsService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
public class SystemSettingsServiceImpl extends AbstractServiceImpl<SystemSettingsMapper, SystemSettings>
    implements SystemSettingsService, ConfigService {

    private final SystemSettingsMapping mapping;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<String> selectOneWithString(String configCode) {
        final Optional<SystemSettings> systemSettingsOpt = this.selectOneByConfigCode(configCode);
        return systemSettingsOpt.map(SystemSettings::getConfigCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T> T getValue(ConfigEnum configEnum) {
        final String text = this.selectOneWithString(configEnum.name())
            .orElse(configEnum.getDefaultValue());
        return configEnum.eval(text);
    }

    public Optional<SystemSettings> selectOneByConfigCode(String configCode) {
        return lambdaQuery().eq(SystemSettings::getConfigCode, configCode).oneOpt();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<SystemSettingsComplexVo> paging(SystemSettingsPagingQo qo) {
        return baseMapper.paging(qo, qo.page())
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
    @Transactional(rollbackFor = Exception.class)
    public void init(List<SystemSettingInitDto> items) {
        final LocalDateTime now = LocalDateTime.now();
        final Map<String, SystemSettingInitDto> newItems = LangUtils.toMap(items, SystemSettingInitDto::getCode);
        List<SystemSettings> exitsItems = LangUtils.groupCallback(newItems.keySet(), this::selectListByConfigCode, 100);
        final Map<String, SystemSettings> oldItems = LangUtils.toMap(exitsItems, SystemSettings::getConfigCode);
        final List<SystemSettings> allItems = newItems.values().parallelStream()
            .map(item -> {
                final String itemCode = item.getCode();

                String remark = item.getRemark();
                if (item.getDeprecated()) {
                    remark = "@废弃 " + remark;
                }

                final SystemSettings result = new SystemSettings()
                    .setRemark(remark)
                    .setTitle(item.getTitle());
                if (oldItems.containsKey(itemCode)) {
                    result.setId(oldItems.get(itemCode).getId())
                        .setLastUpdater(GlobalConstant.SUPPER_ADMIN_USER_ID)
                        .setLastUpdatedAt(now);
                } else {
                    result.setConfigCode(itemCode)
                        .setValue(item.getDefaultValue())
                        .setCreatedAt(now)
                        .setCreator(GlobalConstant.SUPPER_ADMIN_USER_ID);
                }
                return result;
            }).collect(Collectors.toList());
        this.saveOrUpdateBatch(allItems);
    }

    public List<SystemSettings> selectListByConfigCode(Collection<String> configCodes) {
        return lambdaQuery().in(SystemSettings::getConfigCode, configCodes).list();
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
        return Objects.nonNull(baseMapper.existByConfigCodeIgnoreId(code, ignoreId));
    }

    private SystemSettingsComplexVo convertComplex(SystemSettings entity) {
        return mapping.asSystemSettingsComplexVo(entity);
    }
}
