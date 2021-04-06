package tk.fishfish.admin.util.tree;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 树工具类
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public final class TreeUtils {

    private TreeUtils() {
        throw new IllegalStateException("Utils");
    }

    /**
     * 构建树结构
     *
     * @param data              数据
     * @param isParentPredicate 前者是否为后者的父级
     * @param isTopPredicate    元素是否为顶级
     * @return 树型结构
     */
    public static <T extends Tree<T>> List<T> buildTree(
            List<T> data,
            BiPredicate<T, T> isParentPredicate,
            Predicate<T> isTopPredicate
    ) {
        if (isParentPredicate == null) {
            throw new IllegalArgumentException("isParentPredicate不能为空");
        }
        if (isTopPredicate == null) {
            throw new IllegalArgumentException("isParentPredicate不能为空");
        }
        for (T first : data) {
            if (first.getChildren() == null) {
                first.setChildren(new LinkedList<>());
            }
            for (T second : data) {
                // first 为 second 父级
                if (isParentPredicate.test(first, second)) {
                    List<T> children = first.getChildren();
                    children.add(second);
                }
            }
        }
        // 返回顶级
        return data.stream().filter(isTopPredicate).collect(Collectors.toList());
    }

}
