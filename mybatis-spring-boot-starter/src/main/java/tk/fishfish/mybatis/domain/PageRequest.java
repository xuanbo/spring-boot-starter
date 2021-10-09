package tk.fishfish.mybatis.domain;

import lombok.Data;

/**
 * 分页实现
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@Data
public class PageRequest implements Pageable {

    public static final Integer DEFAULT_PAGE = 1;
    public static final Integer DEFAULT_SIZE = 10;

    private Integer page;
    private Integer size;
    private Sort[] sorts;

    public PageRequest() {
        this(DEFAULT_PAGE, DEFAULT_SIZE);
    }

    public PageRequest(Integer page, Integer size) {
        this(DEFAULT_PAGE, DEFAULT_SIZE, null);
    }

    public PageRequest(Integer page, Integer size, Sort[] sorts) {
        this.page = page;
        this.size = size;
        this.sorts = sorts;
    }

}
