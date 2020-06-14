package in.hocg.eagle.modules.wx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.web.AbstractService;
import in.hocg.eagle.modules.wx.entity.WxMpConfig;
import in.hocg.eagle.modules.wx.pojo.qo.WxMpConfigPagingQo;
import in.hocg.eagle.modules.wx.pojo.qo.WxMpConfigSaveQo;
import in.hocg.eagle.modules.wx.pojo.vo.WxMpConfigComplexVo;

import java.util.List;

/**
 * <p>
 * 微信公众号配置表 服务类
 * </p>
 *
 * @author hocgin
 * @since 2020-04-25
 */
public interface WxMpConfigService extends AbstractService<WxMpConfig> {

    void saveOne(WxMpConfigSaveQo qo);

    IPage<WxMpConfigComplexVo> paging(WxMpConfigPagingQo qo);

    WxMpConfigComplexVo selectOne(String appid);

    List<WxMpConfig> selectListByEnabled(Integer enabled);

}
