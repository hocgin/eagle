package in.hocg.eagle.modules.com.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.basic.security.SecurityContext;
import in.hocg.eagle.modules.com.mapstruct.ShortUrlMapping;
import in.hocg.eagle.modules.com.entity.ShortUrl;
import in.hocg.eagle.modules.com.mapper.ShortUrlMapper;
import in.hocg.eagle.modules.com.pojo.qo.shorturl.ShortUrlPagingQo;
import in.hocg.eagle.modules.com.pojo.qo.shorturl.ShortUrlSaveQo;
import in.hocg.eagle.modules.com.pojo.vo.shorturl.ShortUrlComplexVo;
import in.hocg.eagle.modules.com.service.ShortUrlService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ShortUrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * [基础模块] 短链接表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-04
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ShortUrlServiceImpl extends AbstractServiceImpl<ShortUrlMapper, ShortUrl>
    implements ShortUrlService {
    private final ShortUrlMapping mapping;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public String getOrCreateShortUrlCode(String originalUrl) {
        final Optional<ShortUrl> shortUrlOpt = selectOneByOriginalUrl(originalUrl);
        if (shortUrlOpt.isPresent()) {
            return shortUrlOpt.get().getCode();
        }
        final ShortUrl entity = new ShortUrl();
        entity.setCode("tmp");
        entity.setOriginalUrl(originalUrl);
        entity.setCreatedAt(LocalDateTime.now());
        SecurityContext.getCurrentUser().ifPresent(user -> entity.setCreator(user.getId()));
        entity.setEnabled(Enabled.On.getCode());
        validInsert(entity);

        final Long id = entity.getId();
        final ShortUrl update = new ShortUrl();
        update.setId(id);
        final String code = ShortUrlUtils.longToString(id);
        update.setCode(code);
        validUpdateById(update);
        return code;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getOrCreateShortUrlCodeUseRetry(String originalUrl, int retry) {
        try {
            return ((ShortUrlService) AopContext.currentProxy()).getOrCreateShortUrlCode(originalUrl);
        } catch (DuplicateKeyException e) {
            if (retry <= 0) {
                throw ServiceException.wrap("系统繁忙，请稍后");
            }
            return getOrCreateShortUrlCodeUseRetry(originalUrl, --retry);
        }
    }

    @Override
    public Optional<ShortUrl> selectOneByCode(String code) {
        return lambdaQuery()
            .eq(ShortUrl::getCode, code).oneOpt();
    }

    @Override
    public IPage<ShortUrlComplexVo> pagingWithComplex(ShortUrlPagingQo qo) {
        return baseMapper.pagingWithComplex(qo, qo.page())
            .convert(this::convertComplex);
    }

    private ShortUrlComplexVo convertComplex(ShortUrl entity) {
        return mapping.asShortUrlComplexVo(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(ShortUrlSaveQo qo) {
        final Long id = qo.getId();
        ShortUrl entity = mapping.asShortUrl(qo);
        if (Objects.isNull(id)) {
            final String code = getOrCreateShortUrlCode(qo.getOriginalUrl());
            final Optional<ShortUrl> shortUrlOpt = selectOneByCode(code);
            if (shortUrlOpt.isPresent()) {
                final ShortUrl shortUrl = shortUrlOpt.get();
                if (LangUtils.equals(shortUrl.getEnabled(), qo.getEnabled())) {
                    return;
                }

                final ShortUrl update = new ShortUrl();
                update.setId(shortUrl.getId());
                update.setEnabled(qo.getEnabled());
                validUpdateById(update);
            }
        } else {
            validUpdateById(entity);
        }
    }

    private Optional<ShortUrl> selectOneByOriginalUrl(String originalUrl) {
        return lambdaQuery().eq(ShortUrl::getOriginalUrl, originalUrl).oneOpt();
    }
}
