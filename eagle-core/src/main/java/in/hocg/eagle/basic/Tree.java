package in.hocg.eagle.basic;

import com.google.common.collect.Lists;
import in.hocg.eagle.utils.LangUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by hocgin on 2020/2/15.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public final class Tree {
    
    /**
     * 构建树的必须条件
     *
     * @param <T>
     */
    public interface Node<T extends Tree.Node> {
        Integer getId();
        
        Integer getParentId();
        
        void setChildren(List<T> children);
    }
    
    
    /**
     * 获取树
     *
     * @param id
     * @param rootMenu
     * @param <T>
     * @return
     */
    public static <T extends Tree.Node> List<T> getChild(Integer id, Collection<T> rootMenu) {
        // 子菜单
        List<T> childList = new ArrayList<>();
        for (T menu : rootMenu) {
            final Integer parentId = menu.getParentId();
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (LangUtils.equals(id, parentId)) {
                childList.add(menu);
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (T menu : childList) {
            menu.setChildren(getChild(menu.getId(), rootMenu));
        }
        // 递归退出条件
        if (childList.isEmpty()) {
            return Lists.newArrayList();
        }
        return childList;
    }
}
