package in.hocg.eagle.modules.com.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.basic.aspect.logger.Logger;
import in.hocg.eagle.manager.lang.LangManager;
import in.hocg.eagle.manager.lang.dto.IpAndAddressDto;
import in.hocg.eagle.modules.com.mapstruct.RequestLogMapping;
import in.hocg.eagle.modules.com.entity.RequestLog;
import in.hocg.eagle.modules.com.mapper.RequestLogMapper;
import in.hocg.eagle.modules.com.pojo.qo.requestlog.RequestLogPagingQo;
import in.hocg.eagle.modules.com.pojo.vo.requestlog.RequestLogComplexVo;
import in.hocg.eagle.modules.com.service.RequestLogService;
import in.hocg.eagle.utils.ValidUtils;
import in.hocg.eagle.utils.web.UserAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class RequestLogServiceImpl extends AbstractServiceImpl<RequestLogMapper, RequestLog>
    implements RequestLogService {
    private final RequestLogMapping mapping;
    private final LangManager langManager;
    private static final AntPathMatcher MATCHER = new AntPathMatcher();
    private static final List<String> IGNORE_URI = Lists.newArrayList("/api/request-log/*");

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void asyncSave(Logger logger) {
        final String uri = logger.getUri();

        // 如果满足忽略的URI配置
        if (IGNORE_URI.parallelStream().anyMatch(pattern -> MATCHER.match(pattern, uri))) {
            return;
        }

        RequestLog entity = logger.asRequestLog();
        final UserAgent userAgent = new UserAgent(entity.getUserAgent());
        entity.setShell(userAgent.getShell().getName());
        entity.setShellVersion(userAgent.getShellVersion());
        entity.setSupporter(userAgent.getSupporter().getName());
        entity.setSupporterVersion(userAgent.getSupporterVersion());
        entity.setSystemOs(userAgent.getSystem().getName());
        entity.setSystemVersion(userAgent.getShellVersion());
        entity.setEngine(userAgent.getEngine().getName());
        entity.setEngineVersion(userAgent.getEngineVersion());
        entity.setNetType(userAgent.getNetType());
        entity.setPlatform(userAgent.getPlatform().getName());

        final IpAndAddressDto address = langManager.getAddressByIp(entity.getClientIp());
        entity.setNation(address.getNation().orElse(null));
        entity.setProvince(address.getProvince().orElse(null));
        entity.setCity(address.getCity().orElse(null));
        entity.setZipCode(address.getZipCode().orElse(null));
        entity.setOperator(address.getOperator().orElse(null));
        entity.setCityCode(address.getCityCode().orElse(null));

        baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<RequestLogComplexVo> paging(RequestLogPagingQo qo) {
        return baseMapper.paging(qo, qo.page())
            .convert(this::convertComplex);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RequestLogComplexVo selectOne(Long id) {
        final RequestLog entity = getById(id);
        ValidUtils.notNull(entity, "请求日志不存在");
        return this.convertComplex(entity);
    }

    private RequestLogComplexVo convertComplex(RequestLog entity) {
        return mapping.asRequestLogComplexVo(entity);
    }
}
