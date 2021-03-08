package tk.fishfish.mybatis.service.impl;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.fishfish.mybatis.condition.ConditionParser;
import tk.fishfish.mybatis.domain.Page;
import tk.fishfish.mybatis.domain.PageRequest;
import tk.fishfish.mybatis.domain.Pageable;
import tk.fishfish.mybatis.domain.Sort;
import tk.fishfish.mybatis.entity.Entity;
import tk.fishfish.mybatis.pagehelper.PageHelper;
import tk.fishfish.mybatis.repository.Repository;
import tk.fishfish.mybatis.service.BaseService;
import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * 通用服务实现
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public abstract class BaseServiceImpl<T extends Entity> implements BaseService<T> {

    @Autowired
    protected Repository<T> repository;

    @Autowired
    protected ConditionParser conditionParser;

    protected final Class<? extends Entity> entityClazz;

    @SuppressWarnings("unchecked")
    public BaseServiceImpl() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        entityClazz = (Class<? extends Entity>) parameterizedType.getActualTypeArguments()[0];
    }

    @Override
    public Page<T> page(Pageable pageable) {
        return doPage(pageable, () -> repository.selectAll());
    }

    @Override
    public Page<T> page(Object condition, Pageable pageable) {
        Condition cond = conditionParser.parse(entityClazz, condition);
        PageHelper.setOrderBy(cond, Optional.ofNullable(pageable).map(Pageable::getSorts).orElse(null));
        return doPage(pageable, () -> repository.selectByExample(cond));
    }

    protected Page<T> doPage(Pageable pageable, Supplier<List<T>> queryFunc) {
        if (pageable == null) {
            pageable = new PageRequest();
        }
        PageHelper.startPage(pageable, entityClazz);
        PageInfo<T> info = PageInfo.of(queryFunc.get());
        return PageHelper.convert(info);
    }

    @Override
    public List<T> query(Object condition, Sort... sorts) {
        Condition cond = conditionParser.parse(entityClazz, condition);
        PageHelper.setOrderBy(cond, sorts);
        return repository.selectByExample(cond);
    }

    @Override
    public long count(Object condition) {
        Condition cond = conditionParser.parse(entityClazz, condition);
        return repository.selectCountByExample(cond);
    }

    @Override
    public boolean exist(Object condition) {
        return count(condition) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(T entity) {
        entity.setId(generateId());
        repository.insert(entity);
    }

    @Override
    public void insertSelective(T entity) {
        entity.setId(generateId());
        repository.insertSelective(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(T entity) {
        if (entity.getId() == null) {
            throw new MapperException("更新时主键必须指定");
        }
        repository.updateByPrimaryKey(entity);
    }

    @Override
    public void updateSelective(T entity) {
        if (entity.getId() == null) {
            throw new MapperException("更新时主键必须指定");
        }
        repository.updateByPrimaryKeySelective(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(T entity) {
        String id = entity.getId();
        if (id == null) {
            entity.setId(generateId());
            repository.insert(entity);
        } else {
            repository.updateByPrimaryKey(entity);
        }
    }

    @Override
    public T findById(String id) {
        return repository.selectByPrimaryKey(id);
    }

    @Override
    public List<T> findByIds(List<String> ids) {
        Condition condition = new Condition(entityClazz);
        condition.and().andIn("id", ids);
        return repository.selectByExample(condition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        repository.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids) {
        Condition condition = new Condition(entityClazz);
        condition.and().andIn("id", ids);
        repository.deleteByExample(condition);
    }

    /**
     * 生成主键
     *
     * @return 主键
     */
    protected String generateId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}