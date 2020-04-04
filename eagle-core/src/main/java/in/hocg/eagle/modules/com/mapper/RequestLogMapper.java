package in.hocg.eagle.modules.com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.com.entity.RequestLog;
import in.hocg.eagle.modules.com.pojo.qo.requestlog.RequestLogPagingQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [基础模块] 请求日志表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-04-04
 */
@Mapper
public interface RequestLogMapper extends BaseMapper<RequestLog> {

    IPage<RequestLog> pagingWithComplex(@Param("qo") RequestLogPagingQo qo, @Param("page") Page page);
}
