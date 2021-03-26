package tk.fishfish.mybatis.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 排序
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
@Data
public class Sort implements Serializable {

    private String name;

    private Order order;

    public static Sort asc(String name) {
        Sort sort = new Sort();
        sort.setName(name);
        sort.setOrder(Order.DESC);
        return sort;
    }

    public static Sort desc(String name) {
        Sort sort = new Sort();
        sort.setName(name);
        sort.setOrder(Order.DESC);
        return sort;
    }

    public enum Order {

        ASC,

        DESC,

        ;

    }

}
