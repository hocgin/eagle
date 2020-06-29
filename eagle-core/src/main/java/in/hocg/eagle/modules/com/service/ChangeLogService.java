package in.hocg.eagle.modules.com.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractService;
import in.hocg.eagle.basic.constant.datadict.ChangeLogRefType;
import in.hocg.eagle.modules.com.entity.ChangeLog;
import in.hocg.eagle.modules.com.pojo.qo.changelog.ChangeLogPagingQo;
import in.hocg.eagle.modules.com.pojo.vo.changelog.ChangeLogComplexVo;
import in.hocg.eagle.utils.compare.FieldChangeDto;
import lombok.NonNull;

import java.util.List;

/**
 * <p>
 * [基础模块] 业务操作日志表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-11
 */
public interface ChangeLogService extends AbstractService<ChangeLog> {

    void updateLog(@NonNull ChangeLogRefType refType, @NonNull Long refId, List<FieldChangeDto> changes);

    void deleteLog(@NonNull ChangeLogRefType refType, @NonNull Long refId);

    void insertLog(@NonNull ChangeLogRefType refType, @NonNull Long refId);

    IPage<ChangeLogComplexVo> pagingWithComplex(ChangeLogPagingQo qo);
}
