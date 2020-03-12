package in.hocg.eagle.basic.valid;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by hocgin on 2020/3/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class ValidatorUtils {

    public static ValidatorFactory getValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }

    public static Validator getValidation() {
        return getValidatorFactory().getValidator();
    }


}
