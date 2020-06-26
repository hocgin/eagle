package in.hocg.eagle.modules.com.service;

import in.hocg.eagle.modules.com.entity.FieldChange;
import in.hocg.eagle.basic.ext.mybatis.basic.AbstractService;
import in.hocg.eagle.modules.com.pojo.vo.changelog.FieldChangeComplexVo;

import java.util.List;

/**
 * <p>
 * [基础模块] 业务日志-字段变更记录表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-11
 */
public interface FieldChangeService extends AbstractService<FieldChange> {

    void validInsertOrUpdateByChangeLogId(Long changeLogId, List<FieldChange> entities);

    List<FieldChangeComplexVo> selectAllByChangeLogId(Long changeLogId);
}
