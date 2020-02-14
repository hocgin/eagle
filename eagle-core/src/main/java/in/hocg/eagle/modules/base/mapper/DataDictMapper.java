package in.hocg.eagle.modules.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import in.hocg.eagle.modules.base.entity.DataDict;
import in.hocg.eagle.modules.base.entity.DataDictItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * <p>
 * [基础模块] 数据字典表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-02-14
 */
@Mapper
public interface DataDictMapper extends BaseMapper<DataDict> {
    
    /**
     * 查询数据项
     *
     * @param typeCode
     * @param itemCode
     * @return
     */
    Optional<DataDictItem> selectOneByDictIdAndCode(@Param("typeCode") String typeCode,
                                                    @Param("itemCode") String itemCode);
}
