package in.hocg.eagle.modules.com.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.ChangeLogChangeType;
import in.hocg.eagle.basic.constant.datadict.ChangeLogRefType;
import in.hocg.eagle.basic.lang.SNCode;
import in.hocg.eagle.basic.ext.security.SecurityContext;
import in.hocg.eagle.basic.ext.security.User;
import in.hocg.eagle.modules.com.mapstruct.ChangeLogMapping;
import in.hocg.eagle.modules.com.mapstruct.FieldChangeMapping;
import in.hocg.eagle.modules.com.entity.ChangeLog;
import in.hocg.eagle.modules.com.mapper.ChangeLogMapper;
import in.hocg.eagle.modules.com.pojo.qo.changelog.ChangeLogPagingQo;
import in.hocg.eagle.modules.com.pojo.vo.changelog.ChangeLogComplexVo;
import in.hocg.eagle.modules.com.service.ChangeLogService;
import in.hocg.eagle.modules.com.service.FieldChangeService;
import in.hocg.eagle.utils.compare.ChangeLogDto;
import in.hocg.eagle.utils.compare.FieldChangeDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * [基础模块] 业务操作日志表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-11
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ChangeLogServiceImpl extends AbstractServiceImpl<ChangeLogMapper, ChangeLog>
    implements ChangeLogService {
    private final ChangeLogMapping mapping;
    private final FieldChangeMapping fieldChangeMapping;
    private final FieldChangeService fieldChangeService;
    private final SNCode snCode;

    @Transactional(rollbackFor = Exception.class)
    public void insert(ChangeLogDto dto) {
        ChangeLog entity = mapping.asChangeLog(dto);
        entity.setLogSn(snCode.getBusinessSNCode());
        validInsert(entity);
        final Long changeLogId = entity.getId();
        final List<FieldChangeDto> change = dto.getChange();
        if (!change.isEmpty()) {
            fieldChangeService.validInsertOrUpdateByChangeLogId(changeLogId, change.parallelStream()
                .map(fieldChange -> fieldChangeMapping.asFieldChange(fieldChange).setChangeLog(changeLogId))
                .collect(Collectors.toList()));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLog(@NonNull ChangeLogRefType refType, @NonNull Long refId, List<FieldChangeDto> changes) {
        final ChangeLogDto result = new ChangeLogDto();
        result.setChangeType(ChangeLogChangeType.Update.getCode());
        result.setCreatedAt(LocalDateTime.now());
        result.setRefId(refId);
        result.setRefType(refType.getCode());
        final Optional<User> userOpt = SecurityContext.getCurrentUser();
        userOpt.ifPresent(user -> result.setCreator(user.getId()));
        result.setChange(changes);
        this.insert(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLog(@NonNull ChangeLogRefType refType, @NonNull Long refId) {
        final ChangeLogDto result = new ChangeLogDto();
        result.setChangeType(ChangeLogChangeType.Delete.getCode());
        result.setCreatedAt(LocalDateTime.now());
        result.setRefId(refId);
        result.setRefType(refType.getCode());
        final Optional<User> userOpt = SecurityContext.getCurrentUser();
        userOpt.ifPresent(user -> result.setCreator(user.getId()));
        result.setChange(Lists.newArrayList());
        this.insert(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertLog(@NonNull ChangeLogRefType refType, @NonNull Long refId) {
        final ChangeLogDto result = new ChangeLogDto();
        result.setChangeType(ChangeLogChangeType.Insert.getCode());
        result.setCreatedAt(LocalDateTime.now());
        result.setRefId(refId);
        result.setRefType(refType.getCode());
        final Optional<User> userOpt = SecurityContext.getCurrentUser();
        userOpt.ifPresent(user -> result.setCreator(user.getId()));
        result.setChange(Lists.newArrayList());
        this.insert(result);
    }

    @Override
    public IPage<ChangeLogComplexVo> pagingWithComplex(ChangeLogPagingQo qo) {
        return baseMapper.pagingWithComplex(qo, qo.page())
            .convert(this::convertComplex);
    }

    private ChangeLogComplexVo convertComplex(ChangeLog entity) {
        final Long id = entity.getId();
        ChangeLogComplexVo result = mapping.asChangeLogComplexVo(entity);
        return result.setChanges(fieldChangeService.selectAllByChangeLogId(id));
    }

}
