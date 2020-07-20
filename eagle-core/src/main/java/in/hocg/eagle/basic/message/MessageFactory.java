package in.hocg.eagle.basic.message;

import in.hocg.eagle.basic.message.core.local.LocalMessageFactory;
import in.hocg.eagle.basic.message.core.normal.NormalMessageFactory;
import in.hocg.eagle.basic.message.core.transactional.TransactionalMessageFactory;

/**
 * Created by hocgin on 2020/7/20.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class MessageFactory {

    public static TransactionalMessageFactory transactional() {
        return TransactionalMessageFactory.ME;
    }

    public static LocalMessageFactory local() {
        return LocalMessageFactory.ME;
    }

    public static NormalMessageFactory normal() {
        return NormalMessageFactory.ME;
    }
}
