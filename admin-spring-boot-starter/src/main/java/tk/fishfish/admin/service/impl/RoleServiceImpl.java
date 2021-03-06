package tk.fishfish.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;
import tk.fishfish.admin.dto.Select;
import tk.fishfish.admin.entity.Role;
import tk.fishfish.admin.entity.RoleResource;
import tk.fishfish.admin.repository.RoleRepository;
import tk.fishfish.admin.repository.RoleResourceRepository;
import tk.fishfish.admin.repository.UserRoleRepository;
import tk.fishfish.admin.security.UserContextHolder;
import tk.fishfish.admin.service.RoleService;
import tk.fishfish.execption.BizException;
import tk.fishfish.mybatis.service.impl.BaseServiceImpl;

import javax.annotation.Priority;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 角色
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
@Service
@Priority(10)
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleResourceRepository roleResourceRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        userRoleRepository.deleteByRoleId(id);
        roleResourceRepository.deleteByRoleId(id);
        super.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids) {
        userRoleRepository.deleteByRoleIds(ids);
        roleResourceRepository.deleteByRoleIds(ids);
        super.deleteByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grant(String id, List<Select<String>> selects) {
        if (CollectionUtils.isEmpty(selects)) {
            return;
        }
        List<RoleResource> selected = new LinkedList<>();
        List<String> unSelected = new LinkedList<>();
        for (Select<String> select : selects) {
            if (select.isSelected()) {
                RoleResource roleResource = new RoleResource();
                roleResource.setId(generateId());
                roleResource.setRoleId(id);
                roleResource.setResourceId(select.getData());
                selected.add(roleResource);
            } else {
                unSelected.add(select.getData());
            }
        }
        if (!selected.isEmpty()) {
            log.info("角色授权资源，新增 {} 条，id: {}", selected.size(), id);
            roleResourceRepository.insertList(selected);
        }
        if (!unSelected.isEmpty()) {
            log.info("角色授权资源，取消 {} 条，id: {}", unSelected.size(), id);
            roleResourceRepository.deleteByRoleIdAndResourceIds(id, unSelected);
        }
    }

    @Override
    public void beforeInsert(Role role) {
        if (roleRepository.findByCode(role.getCode()) != null) {
            throw BizException.of(400, "角色编码重复: %s", role.getCode());
        }
        role.setCreatedAt(new Date());
        role.setCreatedBy(UserContextHolder.username());
    }

    @Override
    public void beforeUpdate(Role role) {
        String roleId = Optional.ofNullable(roleRepository.findByCode(role.getCode()))
                .map(Role::getId)
                .orElse("");
        if (!StringUtils.equals(roleId, role.getId())) {
            throw BizException.of(400, "角色编码重复: %s", role.getCode());
        }
        role.setUpdatedAt(new Date());
        role.setUpdatedBy(UserContextHolder.username());
    }

}
