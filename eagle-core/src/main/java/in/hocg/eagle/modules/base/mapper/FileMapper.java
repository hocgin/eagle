package in.hocg.eagle.modules.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import in.hocg.eagle.modules.base.entity.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文件引用表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-02-19
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {


    List<File> selectListByRelTypeAndRelIdOrderBySortDescAndCreatedAtDesc(@Param("relType") Integer relType, @Param("relId") Long relId);
}
