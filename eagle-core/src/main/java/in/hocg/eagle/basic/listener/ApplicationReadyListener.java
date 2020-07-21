package in.hocg.eagle.basic.listener;

import com.google.common.collect.Lists;
import in.hocg.eagle.basic.constant.config.ConfigEnum;
import in.hocg.eagle.basic.constant.datadict.DataDictEnum;
import in.hocg.eagle.basic.constant.datadict.Enabled;
import in.hocg.eagle.basic.ext.web.SpringContext;
import in.hocg.eagle.modules.com.pojo.dto.DataDictInitDto;
import in.hocg.eagle.modules.com.pojo.dto.SystemSettingInitDto;
import in.hocg.eagle.modules.com.service.DataDictService;
import in.hocg.eagle.modules.com.service.SystemSettingsService;
import in.hocg.eagle.utils.clazz.ClassUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by hocgin on 2020/6/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.loadConfigs();
        this.loadDataDict();
    }

    @ApiModelProperty("加载系统配置")
    private void loadConfigs() {
        List<SystemSettingInitDto> items = Arrays.stream(ConfigEnum.values()).parallel()
            .map(config -> new SystemSettingInitDto()
                .setDeprecated(ClassUtils.getField(ConfigEnum.class, config.name())
                    .orElseThrow(IllegalArgumentException::new)
                    .isAnnotationPresent(Deprecated.class))
                .setDefaultValue(config.getDefaultValue())
                .setRemark(config.getRemark())
                .setTitle(config.getTitle())
                .setCode(config.name())).collect(Collectors.toList());
        SpringContext.getBean(SystemSettingsService.class).init(items);
    }

    @ApiModelProperty("加载数据字典")
    private void loadDataDict() {
        final List<Class<DataDictEnum>> classes = ClassUtils.getClassAllImpl(DataDictEnum.class);
        List<DataDictInitDto> items = Lists.newArrayList();
        for (Class<DataDictEnum> aClass : classes) {
            try {
                final String key = (String) aClass.getField(DataDictEnum.KEY_FIELD_NAME).get(null);
                final ApiModel apiModel = aClass.getAnnotation(ApiModel.class);
                String enumTitle = aClass.getSimpleName() + "未使用@ApiModel";
                if (Objects.nonNull(apiModel)) {
                    enumTitle = apiModel.value();
                }
                final DataDictInitDto item = new DataDictInitDto().setCode(key)
                    .setEnabled(Enabled.valueOf(!aClass.isAnnotationPresent(Deprecated.class)).getCode())
                    .setTitle(enumTitle);
                final List<DataDictInitDto.Item> children = Lists.newArrayList();
                for (DataDictEnum itemEnum : aClass.getEnumConstants()) {
                    final Class<? extends DataDictEnum> itemEnumClass = itemEnum.getClass();
                    children.add(new DataDictInitDto.Item()
                        .setTitle(itemEnum.getName())
                        .setEnabled(Enabled.valueOf(!itemEnumClass.isAnnotationPresent(Deprecated.class)).getCode())
                        .setCode(String.valueOf(itemEnum.getCode())));
                }
                item.setChildren(children);
                items.add(item);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new UnsupportedOperationException(aClass + "未找到数据字典标识" + DataDictEnum.KEY_FIELD_NAME);
            }
        }
        SpringContext.getBean(DataDictService.class).init(items);
    }
}
