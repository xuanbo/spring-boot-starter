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

    public enum Order {

        ASC,

        DESC,

        ;

    }

}
