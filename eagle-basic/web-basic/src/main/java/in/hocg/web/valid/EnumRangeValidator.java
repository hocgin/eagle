package in.hocg.web.valid;


import in.hocg.web.constant.CodeEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by hocgin on 2020/2/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class EnumRangeValidator implements ConstraintValidator<EnumRange, Serializable> {
    private Class<? extends Enum>[] enumClass;

    @Override
    public void initialize(EnumRange constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Serializable code, ConstraintValidatorContext context) {
        if (Objects.isNull(code)) {
            return true;
        }

        CodeEnum intEnum;
        for (Class<? extends Enum> aClass : enumClass) {
            if (CodeEnum.class.isAssignableFrom(aClass)) {
                for (Enum enumConstant : aClass.getEnumConstants()) {
                    intEnum = (CodeEnum) enumConstant;
                    if (intEnum.eq(code)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
