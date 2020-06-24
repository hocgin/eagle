package in.hocg.eagle.basic.aspect;

import in.hocg.eagle.modules.com.entity.DataDictItem;
import in.hocg.eagle.modules.com.service.DataDictService;
import in.hocg.eagle.modules.pms.entity.ProductCategory;
import in.hocg.eagle.modules.pms.service.ProductCategoryService;
import in.hocg.eagle.modules.ums.entity.Account;
import in.hocg.eagle.modules.ums.entity.Authority;
import in.hocg.eagle.modules.ums.service.AccountService;
import in.hocg.eagle.modules.ums.service.AuthorityService;
import in.hocg.basic.named.NamedHandler;
import in.hocg.basic.named.NamedService;
import in.hocg.basic.named.NamedType;
import in.hocg.web.utils.LangUtils;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by hocgin on 2020/2/13.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class DefaultNamedService implements NamedService {

    private final DataDictService service;
    private final AccountService accountService;
    private final AuthorityService authorityService;
    private final ProductCategoryService productCategoryService;

    @NamedHandler(NamedType.DataDict)
    public Object selectOneByDataDict(Object id, String[] args) {
        if (Objects.isNull(id)) {
            return null;
        }
        final String type = args[0];
        final String item = LangUtils.toString(id);
        if (Strings.isBlank(type) || Strings.isBlank(item)) {
            return null;
        }
        final Optional<DataDictItem> dataDictItemOptional = service.selectOneByDictIdAndCode(type, item);
        return dataDictItemOptional.<Object>map(DataDictItem::getTitle).orElse(null);
    }

    @NamedHandler(NamedType.Nickname)
    public Object selectOneNicknameByAccountId(Object id, String[] args) {
        if (Objects.isNull(id)) {
            return null;
        }
        final Account entity = accountService.getById((Long) id);
        if (Objects.isNull(entity)) {
            return null;
        }
        return entity.getNickname();
    }

    @NamedHandler(NamedType.AuthorityTitle)
    public Object selectOneAuthorityTitleByAuthorityId(Object id, String[] args) {
        if (Objects.isNull(id)) {
            return null;
        }
        final Authority entity = authorityService.getById((Long) id);
        if (Objects.isNull(entity)) {
            return null;
        }
        return entity.getTitle();
    }

    @NamedHandler(NamedType.ProductCategoryName)
    public Object selectOneProductCategoryNameByProductCategoryId(Object id, String[] args) {
        if (Objects.isNull(id)) {
            return null;
        }
        final ProductCategory entity = productCategoryService.getById((Long) id);
        if (Objects.isNull(entity)) {
            return null;
        }
        return entity.getTitle();
    }
}
