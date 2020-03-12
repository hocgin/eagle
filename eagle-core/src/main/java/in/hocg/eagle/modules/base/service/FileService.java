package in.hocg.eagle.modules.base.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.base.entity.File;
import in.hocg.eagle.modules.base.pojo.qo.file.UploadFileDto;
import in.hocg.eagle.modules.base.pojo.vo.file.FileVo;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 文件引用表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-02-19
 */
public interface FileService extends AbstractService<File> {

    /**
     * 上传文件
     *
     * @param dto
     */
    void upload(UploadFileDto dto);

    /**
     * 查询文件列表
     *
     * @param relType
     * @param relId
     * @return
     */
    List<FileVo> selectListByRelTypeAndRelId2(@NotNull File.RelType relType,
                                              @NotNull Long relId);
}
