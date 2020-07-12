package in.hocg.eagle.modules.com.api;

import in.hocg.eagle.modules.com.api.ro.UploadFileRo;
import in.hocg.eagle.modules.com.api.vo.FileVo;

import java.util.List;

/**
 * Created by hocgin on 2020/6/30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface FileAPI {
    /**
     * 上传文件
     *
     * @param ro
     */
    void upload(UploadFileRo ro);

    List<FileVo> selectListByRelTypeAndRelId2(Integer relType, Long relId);
}
