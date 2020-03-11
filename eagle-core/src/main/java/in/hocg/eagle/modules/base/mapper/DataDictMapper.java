package in.hocg.eagle.modules.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import in.hocg.eagle.modules.base.pojo.qo.datadict.DataDictSearchQo;
import in.hocg.eagle.modules.base.entity.DataDict;
import in.hocg.eagle.modules.base.entity.DataDictItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
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

    /**
     * 根据 typeCode 查询数据字典列表
     *
     * @param typeCode
     * @param enabled
     * @return
     */
    List<DataDictItem> selectListDictItemByCodeAndEnabled(@Param("typeCode") String typeCode,
                                                          @Param("enabled") Integer enabled);

    IPage<DataDict> search(@Param("qo") DataDictSearchQo qo, Page page);

    Integer countByCodeIgnoreId(@Param("code") String code, @Param("id") Long id);
}
