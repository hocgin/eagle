package in.hocg.eagle.modules.wx.service;

import in.hocg.eagle.basic.ext.mybatis.core.AbstractService;
import in.hocg.eagle.modules.wx.entity.WxMpUserTagsRelation;

import java.util.List;

/**
 * <p>
 * [微信模块] 标签x用户表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-19
 */
public interface WxMpUserTagsRelationService extends AbstractService<WxMpUserTagsRelation> {

    void batchInsertIfNotExist(Long tagsId, List<WxMpUserTagsRelation> entity);

    /**
     * 设置标签
     *
     * @param tagsId
     * @param wxUserId
     */
    void setTagWithinUser(Long tagsId, List<Long> wxUserId);

    /**
     * 取消标签
     *
     * @param tagsId
     * @param wxUserId
     */
    void unsetTagWithinUser(Long tagsId, List<Long> wxUserId);

    void deleteByTagsId(Long id);
}
