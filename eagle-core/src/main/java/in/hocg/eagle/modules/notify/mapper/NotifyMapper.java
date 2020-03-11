package in.hocg.eagle.modules.notify.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.notify.pojo.qo.notify.SearchNotifyPagingQo;
import in.hocg.eagle.modules.notify.entity.Notify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [消息模块] 通知表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-03-04
 */
@Mapper
public interface NotifyMapper extends BaseMapper<Notify> {
    IPage<Notify> search(@Param("qo") SearchNotifyPagingQo qo, @Param("page") Page page);
}
