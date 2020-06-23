package in.hocg.eagle.modules.com.apimpl;

import in.hocg.eagle.modules.com.api.ChangeLogAPI;
import in.hocg.eagle.modules.com.service.ChangeLogService;
import in.hocg.web.constant.datadict.ChangeLogRefType;
import in.hocg.web.utils.compare.FieldChangeDto;
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
public class ChangeLogAPIImpl implements ChangeLogAPI {
    private final ChangeLogService service;

    @Override
    public void updateLog(ChangeLogRefType refType, Long id, List<FieldChangeDto> changes) {
        service.updateLog(refType, id, changes);
    }
}
