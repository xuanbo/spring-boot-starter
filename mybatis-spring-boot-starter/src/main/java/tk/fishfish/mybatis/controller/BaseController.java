package tk.fishfish.mybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tk.fishfish.mybatis.entity.Entity;
import tk.fishfish.mybatis.service.BaseService;

import java.util.List;

/**
 * 通用API
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public abstract class BaseController<T extends Entity> {

    @Autowired
    private BaseService<T> service;

    @GetMapping("/{id}")
    public T findById(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    public T insert(@RequestBody T entity) {
        service.insert(entity);
        return entity;
    }

    @PutMapping
    public T update(@RequestBody T entity) {
        service.update(entity);
        return entity;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        service.deleteById(id);
    }

    @DeleteMapping("/batch")
    public void deleteByIds(@RequestBody List<String> ids) {
        service.deleteByIds(ids);
    }

}
