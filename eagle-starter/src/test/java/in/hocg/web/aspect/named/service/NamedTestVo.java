package in.hocg.web.aspect.named.service;

import in.hocg.web.aspect.named.InjectNamed;
import in.hocg.web.aspect.named.Named;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by hocgin on 2020/2/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@InjectNamed
@Accessors(chain = true)
public class NamedTestVo {

    private String code;

    @Named(idFor = "code")
    private String codeName;
}