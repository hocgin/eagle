package in.hocg.eagle.basic.constant;

import lombok.experimental.UtilityClass;

/**
 * Created by hocgin on 2020/2/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class PatternConstant {
    // 只能数字或字母
    public final String ONLY_NUMBER_OR_WORD = "^[A-Za-z0-9]+$";
    // URL地址
    public final String URL = "/^https?:\\/\\/(([a-zA-Z0-9_-])+(\\.)?)*(:\\d+)?(\\/((\\.)?(\\?)?=?&?[a-zA-Z0-9_-](\\?)?)*)*$/i";
}
