package tk.fishfish.mybatis.domain;

/**
 * 分页接口
 *
 * @author 奔波儿灞
 * @version 1.3.0
 */
public interface Pageable {

    /**
     * 当前页
     *
     * @return 当前页
     */
    Integer getPage();

    /**
     * 显示数据条数
     *
     * @return 显示数据条数
     */
    Integer getSize();

    /**
     * 排序
     *
     * @return 排序
     */
    Sort[] getSorts();

}
