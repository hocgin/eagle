package in.hocg.eagle.modules.wx.mapper;

import in.hocg.eagle.modules.wx.entity.WxMpUserTagsRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [微信模块] 标签x用户表 Mapper 接口
 * </p>
 *
 * @author hocgin
 * @since 2020-05-19
 */
@Mapper
public interface WxMpUserTagsRelationMapper extends BaseMapper<WxMpUserTagsRelation> {

    void deleteByTagsIdAndOpenId(@Param("tagsId") Long tagsId, @Param("openId") String openId);

    void deleteByTagsId(@Param("tagsId") Long tagsId);
}
