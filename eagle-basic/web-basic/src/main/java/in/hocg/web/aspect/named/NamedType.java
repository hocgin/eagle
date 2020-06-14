package in.hocg.web.aspect.named;

import in.hocg.web.SpringContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

/**
 * Created by hocgin on 2020/2/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RequiredArgsConstructor
public enum NamedType {
    DataDict(SpringContext.getBean(NamedService.class)::selectOneByDataDict),
    Nickname(SpringContext.getBean(NamedService.class)::selectOneNicknameByAccountId),
    ProductCategoryName(SpringContext.getBean(NamedService.class)::selectOneProductCategoryNameByProductCategoryId),
    AuthorityTitle(SpringContext.getBean(NamedService.class)::selectOneAuthorityTitleByAuthorityId);

    @Getter
    private final BiFunction<Object, String[], Object> function;
}
