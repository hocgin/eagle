package in.hocg.eagle.basic.mybatis.tree;

import in.hocg.eagle.basic.AbstractService;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by hocgin on 2020/3/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public interface TreeService<T> extends AbstractService<T> {

    /**
     * 获取指定节点的父节点
     *
     * @param id id
     * @return
     */
    Optional<T> getParent(Long id);

    /**
     * 删除指定节点及其子节点
     *
     * @param id id
     */
    void deleteCurrentAndChildren(Serializable id);
}
