package in.hocg.eagle.modules.wx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractService;
import in.hocg.eagle.basic.pojo.ro.IdRo;
import in.hocg.eagle.modules.wx.entity.WxUser;
import in.hocg.eagle.modules.wx.pojo.qo.user.WxMpUserPagingQo;
import in.hocg.eagle.modules.wx.pojo.qo.user.WxMpUserRefreshQo;
import in.hocg.eagle.modules.wx.pojo.qo.user.WxMpUserSearchQo;
import in.hocg.eagle.modules.wx.pojo.vo.user.WxMpUserComplexVo;

import java.util.List;

/**
 * <p>
 * 微信用户表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-25
 */
public interface WxUserService extends AbstractService<WxUser> {
    void refresh(WxMpUserRefreshQo qo);

    void unsubscribe(String appid, String openid);

    IPage<WxMpUserComplexVo> paging(WxMpUserPagingQo qo);

    WxMpUserComplexVo selectOne(IdRo qo);

    List<WxMpUserComplexVo> complete(WxMpUserSearchQo qo);
}
