package tk.fishfish.mybatis.pagehelper;

import com.github.pagehelper.IPage;
import com.github.pagehelper.PageInfo;
import org.springframework.util.StringUtils;
import tk.fishfish.mybatis.domain.Page;
import tk.fishfish.mybatis.domain.Pageable;
import tk.fishfish.mybatis.domain.Sort;
import tk.fishfish.mybatis.entity.Entity;
import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分页工具类
 *
 * @author 奔波儿灞
 * @version 1.3.0
 */
public final class PageHelper {

    public static void startPage(Pageable pageable) {
        IPage page = convert(pageable);
        com.github.pagehelper.PageHelper.startPage(page);
    }

    public static void startPage(Pageable pageable, Class<? extends Entity> entityClazz) {
        IPage page = convert(pageable, entityClazz);
        com.github.pagehelper.PageHelper.startPage(page);
    }

    public static IPage convert(Pageable pageable) {
        return new PageImpl(pageable.getPage(), pageable.getSize(), orderBy(pageable.getSorts()));
    }

    public static IPage convert(Pageable pageable, Class<? extends Entity> entityClazz) {
        return new PageImpl(pageable.getPage(), pageable.getSize(), orderBy(pageable.getSorts(), entityClazz));
    }

    public static <T> Page<T> convert(PageInfo<T> info) {
        Page<T> page = new Page<>();
        page.setPage(info.getPageNum());
        page.setSize(info.getPageSize());
        page.setTotal(info.getTotal());
        page.setData(info.getList());
        return page;
    }

    public static String orderBy(Sort[] sorts) {
        if (sorts == null || sorts.length == 0) {
            return null;
        }
        return Arrays.stream(sorts).map(sort -> {
            String column = sort.getName();
            if (sort.getOrder() == Sort.Order.ASC) {
                return column + " ASC";
            }
            return column + " DESC";
        }).collect(Collectors.joining(", "));
    }

    public static String orderBy(Sort[] sorts, Class<? extends Entity> entityClazz) {
        if (sorts == null || sorts.length == 0) {
            return null;
        }
        Map<String, String> entityPropertyColumns = entityPropertyColumns(entityClazz);
        return Arrays.stream(sorts).map(sort -> {
            String column = entityPropertyColumns.get(sort.getName());
            if (column == null) {
                throw new MapperException("分页排序参数设置的属性 [" + sort.getName() + "] 未对应字段");
            }
            if (sort.getOrder() == Sort.Order.ASC) {
                return column + " ASC";
            }
            return column + " DESC";
        }).collect(Collectors.joining(", "));
    }

    public static void setOrderBy(Condition condition, Sort[] sorts) {
        String orderBy = PageHelper.orderBy(sorts);
        if (!StringUtils.isEmpty(orderBy)) {
            condition.setOrderByClause(orderBy);
        }
    }

    public static void setOrderBy(Condition condition, Sort[] sorts, Class<? extends Entity> entityClazz) {
        String orderBy = PageHelper.orderBy(sorts, entityClazz);
        if (!StringUtils.isEmpty(orderBy)) {
            condition.setOrderByClause(orderBy);
        }
    }

    private static Map<String, String> entityPropertyColumns(Class<? extends Entity> entityClazz) {
        return EntityHelper.getColumns(entityClazz).stream().collect(Collectors.toMap(EntityColumn::getProperty, EntityColumn::getColumn));
    }

}
