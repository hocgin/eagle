package in.hocg.web.aspect.named;

/**
 * Created by hocgin on 2020/6/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface NamedService {

    Object selectOneByDataDict(Object id, String[] args);

    Object selectOneNicknameByAccountId(Object id, String[] args);

    Object selectOneAuthorityTitleByAuthorityId(Object id, String[] args);

    Object selectOneProductCategoryNameByProductCategoryId(Object id, String[] args);
}
