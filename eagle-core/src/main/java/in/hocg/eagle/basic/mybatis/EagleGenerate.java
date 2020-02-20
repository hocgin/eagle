package in.hocg.eagle.basic.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.google.common.collect.Lists;
import in.hocg.eagle.basic.AbstractEntity;
import in.hocg.eagle.basic.AbstractService;
import in.hocg.eagle.basic.AbstractServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hocgin on 2020/2/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class EagleGenerate {
    private final DataSourceProperties properties;
    List<String> tablePrefix = Lists.newArrayList("T_");
    
    public void generateByTables(String packageName,
                                 String moduleName,
                                 String outputDir,
                                 String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MARIADB)
                .setUrl(properties.getUrl())
                .setUsername(properties.getUsername())
                .setPassword(properties.getPassword())
                .setDriverName(properties.getDriverClassName());
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setEntityLombokModel(true)
                .setEntityTableFieldAnnotationEnable(true)
                .setCapitalMode(true)
                .setEntityColumnConstant(false)
                .setControllerMappingHyphenStyle(true)
                .setRestControllerStyle(true)
                .setTablePrefix(tablePrefix.toArray(new String[]{}))
                
                .setSuperEntityClass(AbstractEntity.class.getName())
                .setSuperServiceClass(AbstractService.class.getName())
                .setSuperServiceImplClass(AbstractServiceImpl.class.getName())
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);
        config.setOpen(false)
                .setActiveRecord(true)
                .setAuthor(System.getProperty("user.name"))
                .setOutputDir(outputDir)
                .setFileOverride(false)
                .setEnableCache(false)
                .setBaseColumnList(false)
                .setBaseResultMap(false)
                .setServiceName("%sService");
        
        new AutoGenerator()
                .setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setTemplate(new TemplateConfig()
                        .setController("/template/mybatis/controller.java")
                        .setServiceImpl("/template/mybatis/serviceImpl.java")
                        .setMapper("/template/mybatis/mapper.java")
                        .setEntity("/template/mybatis/entity.java")
                )
                .setPackageInfo(
                        new PackageConfig()
                                .setModuleName(moduleName)
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("entity")
                ).execute();
    }
}
