package in.hocg.eagle.basic.mybatis;


import in.hocg.eagle.AppApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hocgin on 2020/2/11.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */

@ActiveProfiles("local")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppApplication.class)
public class EagleGenerateTest {
    @Autowired
    private MapperGenerate eagleGenerate;

    @Test
    public void generateByTables() {
        List<String> TABLES = Arrays.asList(
            "wx_material",
            "",
            ""
        );
        String javaPath = "src/main/java";
        String dir = Paths.get(System.getProperty("user.dir"), javaPath).toString();
        eagleGenerate.generateByTables("in.hocg.eagle.modules",
            "wx",
            dir, TABLES.toArray(new String[]{}));
    }
}
