package in.hocg.eagle.modules.com.service.impl;

import in.hocg.eagle.basic.constant.CodeEnum;
import in.hocg.eagle.basic.constant.GlobalConstant;
import in.hocg.eagle.basic.constant.datadict.FileRelType;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.basic.valid.EnumRange;
import in.hocg.eagle.modules.com.api.ro.UploadFileRo;
import in.hocg.eagle.modules.com.entity.File;
import in.hocg.eagle.modules.com.mapper.FileMapper;
import in.hocg.eagle.modules.com.api.vo.FileVo;
import in.hocg.eagle.modules.com.service.FileService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文件引用表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-19
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FileServiceImpl extends AbstractServiceImpl<FileMapper, File>
    implements FileService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upload(UploadFileRo dto) {
        final Long relId = dto.getRelId();
        ValidUtils.notNull(relId, "上传失败，ID 错误");
        final FileRelType relType = CodeEnum.ofThrow(dto.getRelType(), FileRelType.class);
        final List<UploadFileRo.FileDto> files = dto.getFiles();
        deleteAllByRelTypeAndRelId(relType, relId);
        final LocalDateTime now = LocalDateTime.now();
        final Long creator = LangUtils.getOrDefault(dto.getCreator(), GlobalConstant.SUPPER_ADMIN_USER_ID);
        if (CollectionUtils.isEmpty(files)) {
            return;
        }
        final List<File> list = files.parallelStream()
            .map(item -> new File()
                .setRelId(relId)
                .setRelType(relType.getCode())
                .setSort(item.getSort())
                .setCreator(creator)
                .setFilename(item.getFilename())
                .setCreatedAt(now)
                .setFileUrl(item.getUrl())
            ).collect(Collectors.toList());
        this.saveBatch(list);
    }

    @Override
    public List<FileVo> selectListByRelTypeAndRelId2(@EnumRange(enumClass = FileRelType.class) @NotNull Integer fileRelTypeCode,
                                                     @NotNull Long relId) {
        final FileRelType relType = CodeEnum.ofThrow(fileRelTypeCode, FileRelType.class);
        return selectListByRelTypeAndRelIdOrderBySortDescAndCreatedAtDesc(relType, relId)
            .parallelStream()
            .map(item -> new FileVo().setFilename(item.getFilename()).setUrl(item.getFileUrl()))
            .collect(Collectors.toList());
    }

    public List<File> selectListByRelTypeAndRelIdOrderBySortDescAndCreatedAtDesc(@NotNull FileRelType relType, @NotNull Long relId) {
        return baseMapper.selectListByRelTypeAndRelIdOrderBySortDescAndCreatedAtDesc(relType.getCode(), relId);
    }

    private void deleteAllByRelTypeAndRelId(@NotNull FileRelType relType, @NotNull Long relId) {
        baseMapper.deleteAllByRelTypeAndRelId(relType.getCode(), relId);
    }
}
