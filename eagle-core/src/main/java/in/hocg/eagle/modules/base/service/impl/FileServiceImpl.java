package in.hocg.eagle.modules.base.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.modules.base.pojo.qo.file.UploadFileDto;
import in.hocg.eagle.modules.base.pojo.vo.file.FileVo;
import in.hocg.eagle.modules.base.entity.File;
import in.hocg.eagle.modules.base.mapper.FileMapper;
import in.hocg.eagle.modules.base.service.FileService;
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
    public void upload(UploadFileDto dto) {
        final Long relId = dto.getRelId();
        ValidUtils.notNull(relId, "上传失败，ID 错误");
        final File.RelType relType = dto.getRelType();
        ValidUtils.notNull(relType, "上传失败，类型错误");
        final List<UploadFileDto.FileDto> files = dto.getFiles();
        deleteAllByRelTypeAndRelId(relType, relId);
        final LocalDateTime now = LocalDateTime.now();
        if (!CollectionUtils.isEmpty(files)) {
            files.parallelStream()
                .map(item -> new File()
                    .setRelId(relId)
                    .setRelType(relType.getCode())
                    .setSort(item.getSort())
                    .setCreator(dto.getCreator())
                    .setFilename(item.getFilename())
                    .setCreatedAt(now)
                    .setFileUrl(item.getUrl())
                )
                .forEach(file -> baseMapper.insert(file));
        }
    }

    public List<FileVo> selectListByRelTypeAndRelId2(@NotNull File.RelType relType,
                                                     @NotNull Integer relId) {
        return selectListByRelTypeAndRelIdOrderBySortDescAndCreatedAtDesc(relType, relId)
            .parallelStream()
            .map(item -> new FileVo().setFilename(item.getFilename()).setUrl(item.getFileUrl()))
            .collect(Collectors.toList());
    }

    public List<File> selectListByRelTypeAndRelIdOrderBySortDescAndCreatedAtDesc(@NotNull File.RelType relType, @NotNull Integer relId) {
        return baseMapper.selectListByRelTypeAndRelIdOrderBySortDescAndCreatedAtDesc(relType.getCode(), relId);
    }

    private void deleteAllByRelTypeAndRelId(@NotNull File.RelType relType, @NotNull Long relId) {
        final LambdaQueryChainWrapper<File> where = lambdaQuery()
            .eq(File::getRelId, relId)
            .eq(File::getRelType, relType.getCode());
        baseMapper.delete(where);
    }
}
