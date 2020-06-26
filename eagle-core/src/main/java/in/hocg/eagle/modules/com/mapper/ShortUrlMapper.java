package in.hocg.eagle.modules.com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.com.entity.ShortUrl;
import in.hocg.eagle.modules.com.pojo.qo.shorturl.ShortUrlPagingQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [基础模块] 短链接表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-04-04
 */
@Mapper
public interface ShortUrlMapper extends BaseMapper<ShortUrl> {

    IPage<ShortUrl> paging(@Param("qo") ShortUrlPagingQo qo, @Param("page") Page page);
}
