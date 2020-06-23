package in.hocg.eagle.modules.com.service;

import in.hocg.eagle.modules.com.entity.File;
import in.hocg.eagle.modules.com.ro.UploadFileRo;
import in.hocg.eagle.modules.com.vo.file.FileComplexVo;
import in.hocg.web.AbstractService;
import in.hocg.web.constant.datadict.FileRelType;

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
    void upload(UploadFileRo dto);

    /**
     * 查询文件列表
     *
     * @param relType
     * @param relId
     * @return
     */
    List<FileComplexVo> selectListByRelTypeAndRelId2(@NotNull FileRelType relType, @NotNull Long relId);
}
