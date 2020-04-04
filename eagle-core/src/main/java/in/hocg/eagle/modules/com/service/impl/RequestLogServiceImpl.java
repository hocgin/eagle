package in.hocg.eagle.modules.com.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.aspect.logger.Logger;
import in.hocg.eagle.mapstruct.RequestLogMapping;
import in.hocg.eagle.modules.com.entity.RequestLog;
import in.hocg.eagle.modules.com.mapper.RequestLogMapper;
import in.hocg.eagle.modules.com.pojo.qo.requestlog.RequestLogPagingQo;
import in.hocg.eagle.modules.com.pojo.vo.requestlog.RequestLogComplexVo;
import in.hocg.eagle.modules.com.service.RequestLogService;
import in.hocg.eagle.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;

import java.util.List;

/**
 * <p>
 * [基础模块] 请求日志表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-04
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class RequestLogServiceImpl extends AbstractServiceImpl<RequestLogMapper, RequestLog>
    implements RequestLogService {
    private final RequestLogMapping mapping;
    private static final AntPathMatcher MATCHER = new AntPathMatcher();
    private static final List<String> IGNORE_URI = Lists.newArrayList("/api/request-log/*");

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void asyncSave(Logger logger) {
        final String uri = logger.getUri();
        if (IGNORE_URI.parallelStream().noneMatch(pattern -> MATCHER.match(pattern, uri))) {
            RequestLog entity = logger.asRequestLog();
            baseMapper.insert(entity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<RequestLogComplexVo> pagingWithComplex(RequestLogPagingQo qo) {
        return baseMapper.pagingWithComplex(qo, qo.page())
            .convert(this::convertComplex);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RequestLogComplexVo selectOne(Long id) {
        final RequestLog entity = getById(id);
        ValidUtils.notNull(entity, "请求日志不存在");
        return convertComplex(entity);
    }

    private RequestLogComplexVo convertComplex(RequestLog entity) {
        return mapping.asRequestLogComplexVo(entity);
    }
}
