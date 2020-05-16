package in.hocg.eagle.basic.valid;

import in.hocg.eagle.basic.constant.datadict.IntEnum;
import in.hocg.eagle.utils.LangUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by hocgin on 2020/2/16.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class EnumRangeValidator implements ConstraintValidator<EnumRange, Integer> {
    private Class<? extends Enum>[] enumClass;

    @Override
    public void initialize(EnumRange constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext context) {
        IntEnum intEnum;
        for (Class<? extends Enum> aClass : enumClass) {
            if (IntEnum.class.isAssignableFrom(aClass)) {
                for (Enum enumConstant : aClass.getEnumConstants()) {
                    intEnum = (IntEnum) enumConstant;
                    if (LangUtils.equals(intEnum.getCode(), id)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
