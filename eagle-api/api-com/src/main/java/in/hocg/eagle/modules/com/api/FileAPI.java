package in.hocg.eagle.modules.com.api;

import in.hocg.eagle.modules.com.ro.UploadFileRo;
import in.hocg.eagle.modules.com.vo.file.FileComplexVo;
import in.hocg.web.constant.datadict.FileRelType;

import java.util.List;

/**
 * Created by hocgin on 2020/6/23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface FileAPI {
    void upload(UploadFileRo ro);

    List<FileComplexVo> selectListByRelTypeAndRelId2(FileRelType refType, Long refId);
}
