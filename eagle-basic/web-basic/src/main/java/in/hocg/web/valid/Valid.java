package in.hocg.web.valid;


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
        ValidatorUtils.validThrow(groups);
    }
}
