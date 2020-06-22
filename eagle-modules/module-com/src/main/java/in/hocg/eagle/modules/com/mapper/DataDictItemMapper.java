package in.hocg.eagle.modules.com.mapper;

import in.hocg.eagle.modules.com.entity.DataDictItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [基础模块] 数据字典项表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-02-14
 */
@Mapper
public interface DataDictItemMapper extends BaseMapper<DataDictItem> {

    List<DataDictItem> selectListByDictId(@Param("dictId") Long dictId);

    void deleteByDictId(@Param("dictId") Long dictId);

    Integer countDictIdAndCodeIgnoreId(@Param("dictId") Long dictId,
                                     @Param("code") String code,
                                     @Param("id") Long id);
}
