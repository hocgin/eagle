package in.hocg.eagle.mapstruct;

import in.hocg.eagle.modules.com.entity.ShortUrl;
import in.hocg.eagle.modules.com.pojo.qo.shorturl.ShortUrlSaveQo;
import in.hocg.eagle.modules.com.pojo.vo.shorturl.ShortUrlComplexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by hocgin on 2020/4/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Mapper(componentModel = "spring")
public interface ShortUrlMapping {

    @Mapping(target = "code", ignore = true)
    @Mapping(target = "creator", ignore = true)
    ShortUrl asShortUrl(ShortUrlSaveQo qo);

    ShortUrlComplexVo asShortUrlComplexVo(ShortUrl entity);
}
