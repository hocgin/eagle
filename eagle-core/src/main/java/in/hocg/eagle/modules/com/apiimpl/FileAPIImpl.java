package in.hocg.eagle.modules.com.apiimpl;

import in.hocg.eagle.modules.com.api.FileAPI;
import in.hocg.eagle.modules.com.api.ro.UploadFileRo;
import in.hocg.eagle.modules.com.api.vo.FileVo;
import in.hocg.eagle.modules.com.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hocgin on 2020/6/30.
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
    public List<FileVo> selectListByRelTypeAndRelId2(Integer relType, Long relId) {
        return service.selectListByRelTypeAndRelId2(relType, relId);
    }
}
