package tk.fishfish.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;
import tk.fishfish.admin.cache.PermissionCache;
import tk.fishfish.admin.dto.Select;
import tk.fishfish.admin.entity.Resource;
import tk.fishfish.admin.entity.ResourcePermission;
import tk.fishfish.admin.repository.ResourcePermissionRepository;
import tk.fishfish.admin.repository.ResourceRepository;
import tk.fishfish.admin.repository.RoleResourceRepository;
import tk.fishfish.admin.security.UserContextHolder;
import tk.fishfish.admin.service.ResourceService;
import tk.fishfish.admin.util.tree.TreeUtils;
import tk.fishfish.execption.BizException;
import tk.fishfish.mybatis.service.impl.BaseServiceImpl;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Priority;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 资源
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
@Service
@Priority(10)
@RequiredArgsConstructor
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final RoleResourceRepository roleResourceRepository;
    private final ResourcePermissionRepository resourcePermissionRepository;

    private final PermissionCache permissionCache;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        roleResourceRepository.deleteByResourceId(id);
        resourcePermissionRepository.deleteByResourceId(id);
        super.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids) {
        roleResourceRepository.deleteByResourceIds(ids);
        resourcePermissionRepository.deleteByResourceIds(ids);
        super.deleteByIds(ids);
    }

    @Override
    public List<Resource> tree() {
        return TreeUtils.buildTree(
                query(null),
                (first, second) -> StringUtils.equals(first.getId(), second.getParentId()),
                item -> StringUtils.isEmpty(item.getParentId())
        );
    }

    @Override
    public void grant(String id, List<Select<String>> selects) {
        if (CollectionUtils.isEmpty(selects)) {
            return;
        }
        List<ResourcePermission> selected = new LinkedList<>();
        List<String> unSelected = new LinkedList<>();
        for (Select<String> select : selects) {
            if (select.isSelected()) {
                ResourcePermission resourcePermission = new ResourcePermission();
                resourcePermission.setId(generateId());
                resourcePermission.setResourceId(id);
                resourcePermission.setPermissionId(select.getData());
                selected.add(resourcePermission);
            } else {
                unSelected.add(select.getData());
            }
        }
        if (!selected.isEmpty()) {
            log.info("资源授权权限，新增 {} 条，id: {}", selected.size(), id);
            resourcePermissionRepository.insertList(selected);
        }
        if (!unSelected.isEmpty()) {
            log.info("资源授权权限，取消 {} 条，id: {}", unSelected.size(), id);
            resourcePermissionRepository.deleteByResourceIdPermissionIds(id, unSelected);
        }
        // 清除缓存
        permissionCache.evict(id);
    }

    @Override
    protected void beforeInsert(Resource resource) {
        if (resourceRepository.findByCode(resource.getCode()) != null) {
            throw BizException.of(400, "资源编码重复: %s", resource.getCode());
        }
        resource.setCreatedAt(new Date());
        resource.setCreatedBy(UserContextHolder.username());
    }

    @Override
    protected void beforeUpdate(Resource resource) {
        String resourceId = Optional.ofNullable(resourceRepository.findByCode(resource.getCode()))
                .map(Resource::getId)
                .orElse("");
        if (!StringUtils.equals(resourceId, resource.getId())) {
            throw BizException.of(400, "资源编码重复: %s", resource.getCode());
        }
        resource.setUpdatedAt(new Date());
        resource.setUpdatedBy(UserContextHolder.username());
    }

    @Override
    protected void afterDelete(String id) {
        permissionCache.evict();
    }

    @Override
    protected void afterDelete(List<String> ids) {
        permissionCache.evict();
    }

    @Override
    protected void afterDelete(Condition condition) {
        permissionCache.evict();
    }

}
