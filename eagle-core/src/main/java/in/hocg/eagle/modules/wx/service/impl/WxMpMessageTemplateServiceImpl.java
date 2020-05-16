package in.hocg.eagle.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import in.hocg.eagle.basic.AbstractServiceImpl;
import in.hocg.eagle.modules.wx.entity.WxMpMessageTemplate;
import in.hocg.eagle.modules.wx.manager.WxMpManager;
import in.hocg.eagle.modules.wx.mapper.WxMpMessageTemplateMapper;
import in.hocg.eagle.modules.wx.mapstruct.WxMpMessageTemplateMapping;
import in.hocg.eagle.modules.wx.pojo.qo.message.template.RefreshMessageTemplateQo;
import in.hocg.eagle.modules.wx.pojo.qo.message.template.WxMpMessageTemplatePageQo;
import in.hocg.eagle.modules.wx.pojo.vo.message.template.WxMpMessageTemplateComplexVo;
import in.hocg.eagle.modules.wx.service.WxMpMessageTemplateService;
import in.hocg.eagle.utils.LangUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * <p>
 * [微信模块] 消息模版表 服务实现类
 * </p>
 *
 * @author hocgin
 * @since 2020-05-16
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class WxMpMessageTemplateServiceImpl extends AbstractServiceImpl<WxMpMessageTemplateMapper, WxMpMessageTemplate> implements WxMpMessageTemplateService {
    private final WxMpManager wxMpManager;
    private final WxMpMessageTemplateMapping mapping;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refresh(RefreshMessageTemplateQo qo) {
        final String appid = qo.getAppid();
        final LocalDateTime createdAt = qo.getCreatedAt();
        final Long userId = qo.getUserId();

        final List<WxMpMessageTemplate> allPrivateTemplate = wxMpManager.getAllPrivateTemplate(appid);
        final List<WxMpMessageTemplate> allTemplate = selectListByAppid(appid);
        final BiFunction<WxMpMessageTemplate, WxMpMessageTemplate, Boolean> isSameMessageTemplate = (t1, t2) -> LangUtils.equals(t1.getAppid(), t2.getAppid()) && LangUtils.equals(t1.getTemplateId(), t2.getTemplateId());
        final List<WxMpMessageTemplate> mixedList = LangUtils.getMixed(allTemplate, allPrivateTemplate, isSameMessageTemplate);
        List<WxMpMessageTemplate> deleteList = LangUtils.removeIfExits(allTemplate, mixedList, isSameMessageTemplate);
        List<WxMpMessageTemplate> addList = LangUtils.removeIfExits(allPrivateTemplate, mixedList, isSameMessageTemplate);
        this.removeByIds(deleteList.parallelStream().map(WxMpMessageTemplate::getId).filter(Objects::nonNull).collect(Collectors.toList()));
        addList.parallelStream().peek(wxMpMessageTemplate -> wxMpMessageTemplate.setCreatedAt(createdAt).setCreator(userId))
            .forEach(this::validInsertOrUpdate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<WxMpMessageTemplateComplexVo> paging(WxMpMessageTemplatePageQo qo) {
        return baseMapper.paging(qo, qo.page())
            .convert(this::convertComplex);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxMpMessageTemplateComplexVo selectOne(Long id) {
        final WxMpMessageTemplate entity = getById(id);
        return this.convertComplex(entity);
    }

    private WxMpMessageTemplateComplexVo convertComplex(WxMpMessageTemplate entity) {
        return mapping.asWxMpMessageTemplateComplexVo(entity);
    }

    private List<WxMpMessageTemplate> selectListByAppid(String appid) {
        return lambdaQuery().eq(WxMpMessageTemplate::getAppid, appid).list();
    }

}
