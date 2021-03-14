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
    public T single(Object condition) {
        Condition cond = conditionParser.parse(entityClazz, condition);
        return repository.selectOneByExample(cond);
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
        beforeInsert(entity);
        repository.insert(entity);
        afterInsert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertSelective(T entity) {
        entity.setId(generateId());
        beforeInsert(entity);
        repository.insertSelective(entity);
        afterInsert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(T entity) {
        if (entity.getId() == null) {
            throw new MapperException("更新时主键必须指定");
        }
        beforeUpdate(entity);
        repository.updateByPrimaryKey(entity);
        afterUpdate(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSelective(T entity) {
        if (entity.getId() == null) {
            throw new MapperException("更新时主键必须指定");
        }
        beforeUpdate(entity);
        repository.updateByPrimaryKeySelective(entity);
        afterUpdate(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(T entity) {
        String id = entity.getId();
        if (id == null) {
            insert(entity);
        } else {
            update(entity);
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

    /**
     * Insert前调用
     *
     * @param entity 实体
     */
    protected void beforeInsert(T entity) {
    }

    /**
     * Insert后调用
     *
     * @param entity 实体
     */
    protected void afterInsert(T entity) {
    }

    /**
     * Update前调用
     *
     * @param entity 实体
     */
    protected void beforeUpdate(T entity) {
    }

    /**
     * Update后调用
     *
     * @param entity 实体
     */
    protected void afterUpdate(T entity) {
    }

}