package tk.fishfish.mybatis.pagehelper;

import com.github.pagehelper.IPage;
import org.springframework.util.StringUtils;

/**
 * 分页接口实现
 *
 * @author 奔波儿灞
 * @version 1.3.0
 */
public class PageImpl implements IPage {

    private final Integer page;
    private final Integer size;
    private final String orderBy;

    public PageImpl(Integer page, Integer size) {
        this(page, size, null);
    }

    public PageImpl(Integer page, Integer size, String orderBy) {
        this.page = page;
        this.size = size;
        if (StringUtils.isEmpty(orderBy)) {
            this.orderBy = null;
        } else {
            this.orderBy = orderBy;
        }
    }

    @Override
    public Integer getPageNum() {
        return page;
    }

    @Override
    public Integer getPageSize() {
        return size;
    }

    @Override
    public String getOrderBy() {
        return orderBy;
    }

}
