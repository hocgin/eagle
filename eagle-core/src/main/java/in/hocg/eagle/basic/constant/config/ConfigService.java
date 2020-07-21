package in.hocg.eagle.basic.constant.config;

/**
 * Created by hocgin on 2020/7/21.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface ConfigService {
    <T> T getValue(ConfigEnum configEnum);
}
