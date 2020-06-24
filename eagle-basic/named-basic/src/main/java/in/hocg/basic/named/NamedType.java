package in.hocg.basic.named;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by hocgin on 2020/2/17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RequiredArgsConstructor
public enum NamedType {
    Unknown("未知"),
    DataDict("查询枚举值"),
    Nickname("用户昵称"),
    ProductCategoryName("商品品类名"),
    AuthorityTitle("权限名称");

    @Getter
    private final String name;
}
