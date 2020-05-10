package in.hocg.eagle.modules.com.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.modules.com.entity.ShortUrl;
import in.hocg.eagle.modules.com.pojo.qo.shorturl.ShortUrlPagingQo;
import in.hocg.eagle.modules.com.pojo.qo.shorturl.ShortUrlSaveQo;
import in.hocg.eagle.modules.com.pojo.vo.shorturl.ShortUrlComplexVo;

import java.util.Optional;

/**
 * <p>
 * [基础模块] 短链接表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-04
 */
public interface ShortUrlService extends AbstractService<ShortUrl> {

    String getOrCreateShortUrlCode(String originalUrl);

    String getOrCreateShortUrlCodeUseRetry(String originalUrl, int retry);

    Optional<ShortUrl> selectOneByCode(String code);

    IPage<ShortUrlComplexVo> pagingWithComplex(ShortUrlPagingQo qo);

    void saveOne(ShortUrlSaveQo qo);
}
