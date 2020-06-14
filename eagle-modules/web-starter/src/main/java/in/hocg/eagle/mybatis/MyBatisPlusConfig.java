package in.hocg.eagle.mybatis;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import in.hocg.AppApplication;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hocgin on 2020/1/5.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Configuration
@MapperScan(basePackageClasses = AppApplication.class, annotationClass = Mapper.class)
public class MyBatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
