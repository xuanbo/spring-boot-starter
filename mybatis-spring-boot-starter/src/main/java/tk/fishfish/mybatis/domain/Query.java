package tk.fishfish.mybatis.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询
 *
 * @author 奔波儿灞
 * @version 1.3.0
 */
@Data
public class Query<C> implements Serializable {

    /**
     * 查询条件
     */
    private C condition;

    /**
     * 分页
     */
    private PageRequest page;

}
