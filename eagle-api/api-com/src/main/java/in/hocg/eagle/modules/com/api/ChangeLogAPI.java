package in.hocg.eagle.modules.com.api;

import in.hocg.web.constant.datadict.ChangeLogRefType;
import in.hocg.web.utils.compare.FieldChangeDto;

import java.util.List;

/**
 * Created by hocgin on 2020/6/23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface ChangeLogAPI {
    void updateLog(ChangeLogRefType refType, Long id, List<FieldChangeDto> changes);
}
