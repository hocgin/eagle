package in.hocg.eagle.basic.valid;


import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
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
        final Set<ConstraintViolation<Valid>> validate = ValidatorUtils.getValidation()
            .validate(this, groups);
        for (ConstraintViolation<Valid> violation : validate) {
            throw new ValidationException(violation.getMessage());
        }
    }
}
