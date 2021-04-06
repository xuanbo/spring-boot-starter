package tk.fishfish.admin.util.tree;

import java.util.List;

/**
 * 树
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public interface Tree<T> {

    List<T> getChildren();

    void setChildren(List<T> children);

}
