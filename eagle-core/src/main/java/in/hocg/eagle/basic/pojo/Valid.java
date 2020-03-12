package in.hocg.eagle.basic.pojo;

import in.hocg.eagle.basic.exception.ServiceException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by hocgin on 2020/3/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface Valid {

    /**
     * 校验并抛出异常
     *
     * @param groups
     */
    default void validThrow(Class<?>... groups) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        final Set<ConstraintViolation<Valid>> validate = validator.validate(this, groups);
        for (ConstraintViolation<Valid> violation : validate) {
            throw ServiceException.wrap(violation.getMessage());
        }
    }
}
