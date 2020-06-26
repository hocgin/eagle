package in.hocg.eagle.modules.wx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.basic.AbstractService;
import in.hocg.eagle.modules.wx.entity.WxMenu;
import in.hocg.eagle.modules.wx.pojo.qo.menu.WxMenuInsertQo;
import in.hocg.eagle.modules.wx.pojo.qo.menu.WxMenuPagingQo;
import in.hocg.eagle.modules.wx.pojo.qo.menu.WxMenuUpdateQo;
import in.hocg.eagle.modules.wx.pojo.vo.menu.WxMenuComplexVo;

/**
 * <p>
 * [微信模块] 菜单表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-04
 */
public interface WxMenuService extends AbstractService<WxMenu> {

    void insertOne(WxMenuInsertQo qo);

    void updateOne(WxMenuUpdateQo qo);

    IPage<WxMenuComplexVo> paging(WxMenuPagingQo qo);

    WxMenuComplexVo selectOne(Long id);

    void sync(Long id);
}
