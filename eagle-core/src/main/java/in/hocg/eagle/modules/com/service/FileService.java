package in.hocg.eagle.modules.com.service;

import in.hocg.eagle.basic.ext.mybatis.core.AbstractService;
import in.hocg.eagle.basic.constant.datadict.FileRelType;
import in.hocg.eagle.modules.com.entity.File;
import in.hocg.eagle.modules.com.pojo.qo.file.UploadFileDto;
import in.hocg.eagle.modules.com.pojo.vo.file.FileVo;

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
    List<FileVo> selectListByRelTypeAndRelId2(@NotNull FileRelType relType,
                                              @NotNull Long relId);
}
