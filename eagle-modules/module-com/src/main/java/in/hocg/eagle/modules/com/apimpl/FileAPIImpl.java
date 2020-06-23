package in.hocg.eagle.modules.com.apimpl;

import in.hocg.eagle.modules.com.api.FileAPI;
import in.hocg.eagle.modules.com.ro.UploadFileRo;
import in.hocg.eagle.modules.com.service.FileService;
import in.hocg.eagle.modules.com.vo.file.FileComplexVo;
import in.hocg.web.constant.datadict.FileRelType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hocgin on 2020/6/23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FileAPIImpl implements FileAPI {
    private final FileService service;

    @Override
    public void upload(UploadFileRo ro) {
        service.upload(ro);
    }

    @Override
    public List<FileComplexVo> selectListByRelTypeAndRelId2(FileRelType refType, Long refId) {
        return service.selectListByRelTypeAndRelId2(refType, refId);
    }

}
