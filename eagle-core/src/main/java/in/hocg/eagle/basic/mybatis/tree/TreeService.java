package in.hocg.eagle.basic.mybatis.tree;

import in.hocg.eagle.basic.AbstractService;

import java.util.Optional;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface TreeService<T> extends AbstractService<T> {

    Optional<T> getParent(Long id);
}
