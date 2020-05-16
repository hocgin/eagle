package in.hocg.eagle.basic.valid;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by hocgin on 2020/2/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class StringRangeValidator implements ConstraintValidator<StringRange, String> {
    private String[] strings;

    @Override
    public void initialize(StringRange constraintAnnotation) {
        strings = constraintAnnotation.strings();
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext context) {
        for (String string : strings) {
            if (StringUtils.equals(str, string)) {
                return true;
            }
        }
        return false;
    }
}
