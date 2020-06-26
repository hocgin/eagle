package in.hocg.eagle.modules.com.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.basic.AbstractService;
import in.hocg.eagle.basic.aspect.logger.Logger;
import in.hocg.eagle.modules.com.entity.RequestLog;
import in.hocg.eagle.modules.com.pojo.qo.requestlog.RequestLogPagingQo;
import in.hocg.eagle.modules.com.pojo.vo.requestlog.RequestLogComplexVo;

/**
 * <p>
 * [基础模块] 请求日志表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-04
 */
public interface RequestLogService extends AbstractService<RequestLog> {

    void asyncSave(Logger logger);

    IPage<RequestLogComplexVo> paging(RequestLogPagingQo qo);

    RequestLogComplexVo selectOne(Long id);
}
