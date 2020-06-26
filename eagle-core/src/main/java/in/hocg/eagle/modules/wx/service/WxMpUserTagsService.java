package in.hocg.eagle.modules.wx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.basic.AbstractService;
import in.hocg.eagle.modules.wx.entity.WxMpUserTags;
import in.hocg.eagle.modules.wx.pojo.qo.user.tags.*;
import in.hocg.eagle.modules.wx.pojo.vo.user.tags.WxMpUserTagsComplexVo;

/**
 * <p>
 * [微信模块] 用户标签表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-19
 */
public interface WxMpUserTagsService extends AbstractService<WxMpUserTags> {

    /**
     * 从微信同步指定公众号上的所有用户标签
     *
     * @param qo
     */
    void refresh(WxMpUserTagsRefreshQo qo);

    IPage<WxMpUserTagsComplexVo> paging(WxMpUserTagsPageQo qo);

    WxMpUserTagsComplexVo selectOne(Long id);

    void setTagWithinUser(WxMpSetUserTagsQo qo);

    void unsetTagWithinUser(WxMpUnsetUserTagsQo qo);

    void deleteOne(Long id);

    void insertOne(WxMpUserTagsInsertQo qo);
}
