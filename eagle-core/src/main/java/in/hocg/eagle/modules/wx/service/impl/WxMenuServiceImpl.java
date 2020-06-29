package in.hocg.eagle.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.ext.mybatis.core.AbstractServiceImpl;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.constant.CodeEnum;
import in.hocg.eagle.basic.constant.datadict.WxMenuType;
import in.hocg.eagle.basic.exception.ServiceException;
import in.hocg.eagle.basic.ext.security.SecurityContext;
import in.hocg.eagle.modules.wx.entity.WxMenu;
import in.hocg.eagle.modules.wx.manager.WxMpManager;
import in.hocg.eagle.modules.wx.mapper.WxMenuMapper;
import in.hocg.eagle.modules.wx.mapstruct.WxMenuMapping;
import in.hocg.eagle.modules.wx.pojo.qo.menu.WxMenuInsertQo;
import in.hocg.eagle.modules.wx.pojo.qo.menu.WxMenuPagingQo;
import in.hocg.eagle.modules.wx.pojo.qo.menu.WxMenuUpdateQo;
import in.hocg.eagle.modules.wx.pojo.vo.menu.WxMenuComplexVo;
import in.hocg.eagle.modules.wx.service.WxMenuService;
import in.hocg.eagle.modules.wx.service.WxMpConfigService;
import in.hocg.eagle.utils.LangUtils;
import in.hocg.eagle.utils.ValidUtils;
import in.hocg.eagle.utils.string.JsonUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * <p>
 * [微信模块] 菜单表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-04
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class WxMenuServiceImpl extends AbstractServiceImpl<WxMenuMapper, WxMenu> implements WxMenuService {
    private final WxMenuMapping mapping;
    private final WxMpConfigService wxMpConfigService;
    private final WxMpManager wxMpManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOne(WxMenuInsertQo qo) {
        final LocalDateTime requestDateTime = qo.getCreatedAt();
        final Long userId = qo.getUserId();
        final WxMenuType wxMenuType = CodeEnum.ofThrow(qo.getMenuType(), WxMenuType.class);

        if (wxMenuType.equals(WxMenuType.Individuation)) {
            ValidUtils.notNull(qo.getMatchRule(), "个性化菜单匹配规则不能为空");
        } else {
            qo.setMatchRule(null);
        }
        WxMenu entity = mapping.asWxMenu(qo);
        entity.setButton(LangUtils.callIfNotNull(qo.getButton(), JsonUtils::toJSONString).orElse(null));
        entity.setMatchRule(LangUtils.callIfNotNull(qo.getMatchRule(), JsonUtils::toJSONString).orElse(null));
        entity.setMenuType(wxMenuType.getCode());
        entity.setCreatedAt(requestDateTime);
        entity.setCreator(userId);
        validInsertOrUpdate(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(WxMenuUpdateQo qo) {
        final LocalDateTime requestDateTime = qo.getCreatedAt();
        final Long userId = qo.getUserId();
        WxMenu entity = mapping.asWxMenu(qo);
        entity.setButton(LangUtils.callIfNotNull(qo.getButton(), JsonUtils::toJSONString).orElse(null));
        entity.setMatchRule(LangUtils.callIfNotNull(qo.getMatchRule(), JsonUtils::toJSONString).orElse(null));
        entity.setLastUpdatedAt(requestDateTime);
        entity.setLastUpdater(userId);
        validInsertOrUpdate(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<WxMenuComplexVo> paging(WxMenuPagingQo qo) {
        return baseMapper.paging(qo, qo.page())
            .convert(this::convertComplex);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxMenuComplexVo selectOne(Long id) {
        final WxMenu entity = getById(id);
        ValidUtils.notNull(entity, "菜单不存在");
        return this.convertComplex(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sync(Long id) {
        autoSyncMenu(id);
    }

    @Override
    public void validEntity(WxMenu entity) {
        super.validEntity(entity);
        final String appid = entity.getAppid();
        if (Strings.isNotBlank(appid)) {
            ValidUtils.notNull(wxMpConfigService.getById(appid), "APPID 错误, 未找到该微信公众号");
        }
    }

    private WxMenuComplexVo convertComplex(WxMenu entity) {
        final WxMenuComplexVo result = mapping.asWxMenuComplexVo(entity);
        result.setButton(LangUtils.callIfNotNull(entity.getButton(), s -> JsonUtils.parseArray(s, WxMenuComplexVo.Button.class)).orElse(Collections.emptyList()));
        result.setMatchRule(LangUtils.callIfNotNull(entity.getMatchRule(), s -> JsonUtils.parseObject(s, WxMenuComplexVo.MatchRule.class)).orElse(null));
        return result;
    }

    /**
     * 同步菜单到公众号
     *
     * @param id
     */
    private void autoSyncMenu(Long id) {
        final WxMenu entity = getById(id);
        final LocalDateTime now = LocalDateTime.now();
        final Long userId = SecurityContext.getCurrentUserId();

        final WxMenu updated = new WxMenu();
        updated.setId(id);
        updated.setUploadedAt(now);
        updated.setUploader(userId);
        // 如果是(启用状态 && 通用菜单)
        if (LangUtils.equals(entity.getEnabled(), Enabled.On.getCode())
            && LangUtils.equals(entity.getMenuType(), WxMenuType.General.getCode())) {
            wxMpManager.setWxGeneralMenu(entity);
            updated.setEnabled(Enabled.On.getCode());
        }
        // 如果是(启用状态 && 个性化菜单)
        else if (LangUtils.equals(entity.getEnabled(), Enabled.On.getCode())
            && LangUtils.equals(entity.getMenuType(), WxMenuType.Individuation.getCode())) {
            wxMpManager.removeWxIndividuationMenu(entity);
            final String menuId = wxMpManager.setWxIndividuationMenu(entity);
            updated.setEnabled(Enabled.On.getCode());
            updated.setMenuId(menuId);
        }
        // 如果是(禁用状态 && 通用菜单)
        else if (LangUtils.equals(entity.getEnabled(), Enabled.Off.getCode())
            && LangUtils.equals(entity.getMenuType(), WxMenuType.General.getCode())) {
            this.updateEnabledByAppId(entity.getAppid(), Enabled.Off);
            wxMpManager.removeWxGeneralMenu(entity);
            return;
        }
        // 如果是(禁用状态 && 个性化菜单)
        else if (LangUtils.equals(entity.getEnabled(), Enabled.Off.getCode())
            && LangUtils.equals(entity.getMenuType(), WxMenuType.Individuation.getCode())) {
            updated.setEnabled(Enabled.Off.getCode());
            wxMpManager.removeWxIndividuationMenu(entity);
        } else {
            throw ServiceException.wrap("数据错误");
        }
        baseMapper.updateById(entity);
    }

    private void updateEnabledByAppId(@NonNull String appid, Enabled enabled) {
        lambdaUpdate().set(WxMenu::getEnabled, enabled.getCode())
            .set(WxMenu::getUploadedAt, LocalDateTime.now())
            .set(WxMenu::getUploader, SecurityContext.getCurrentUserId())
            .eq(WxMenu::getAppid, appid).update();
    }

}
