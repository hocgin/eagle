package in.hocg.generator.core;

import com.baomidou.mybatisplus.annotation.DbType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/5/29.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Getter
@RequiredArgsConstructor
public enum DataSource {
    DEFAULT(DbType.MYSQL, "jdbc:mysql://mysql.localhost:3306/db_springcloud?useSSL=false&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&allowPublicKeyRetrieval=true",
        com.mysql.cj.jdbc.Driver.class, "root", "hocgin");
    private final DbType dbType;
    private final String url;
    private final Class<? extends java.sql.Driver> driverName;
    private final String username;
    private final String password;
}
