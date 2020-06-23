package in.hocg.eagle.modules.crm.apiimpl;

import in.hocg.basic.api.vo.CommentComplexVo;
import in.hocg.eagle.modules.crm.api.CommentAPI;
import in.hocg.eagle.modules.crm.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Created by hocgin on 2020/6/23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class CommentAPIImpl implements CommentAPI {
    private final CommentService service;

    @Override
    public CommentComplexVo selectOne(Long id) {
        return service.selectOne(id);
    }
}
