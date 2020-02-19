package in.hocg.eagle.modules.base.service;

import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.mapstruct.qo.file.UploadFileDto;
import in.hocg.eagle.modules.base.entity.File;

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
    
}
