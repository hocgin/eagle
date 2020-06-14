package in.hocg.eagle.basic;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hocgin on 2020/4/4.
 * email: hocgin@gmail.com
 * 加密工具
 *
 * @author hocgin
 */
public class JasyptTests extends AbstractSpringBootTest {
    @Autowired
    StringEncryptor jasyptStringEncryptor;

    @Test
    public void encrypt() {
        System.out.println("加密: " + jasyptStringEncryptor.encrypt("root"));
        System.out.println("加密hocgin: " + jasyptStringEncryptor.encrypt("hocgin"));
    }

    @Test
    public void decrypt() {
        System.out.println("解密: " + jasyptStringEncryptor.decrypt("K57tbfJNa4BNTg8PI/g09A=="));
    }
}
