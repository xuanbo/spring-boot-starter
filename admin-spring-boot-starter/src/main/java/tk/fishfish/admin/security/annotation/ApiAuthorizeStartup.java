package tk.fishfish.admin.security.annotation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import tk.fishfish.admin.entity.Permission;
import tk.fishfish.admin.service.PermissionService;

/**
 * API权限启动同步
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
@Component
@Order(100)
@RequiredArgsConstructor
public class ApiAuthorizeStartup implements CommandLineRunner {

    private final RequestMappingInfoHandlerMapping requestMappingInfoHandlerMapping;

    private final PermissionService permissionService;

    @Override
    public void run(String... args) {
        log.info("同步API权限开始");
        requestMappingInfoHandlerMapping.getHandlerMethods().forEach((info, handlerMethod) -> {
            ApiAuthorize apiAuthorize = handlerMethod.getMethodAnnotation(ApiAuthorize.class);
            if (apiAuthorize == null) {
                return;
            }
            String[] methods = info.getMethodsCondition().getMethods().stream().map(RequestMethod::name).toArray(String[]::new);
            String path = String.join("/", info.getPatternsCondition().getPatterns());
            Permission permission = permissionService.findById(apiAuthorize.code());
            if (permission == null) {
                log.info("新增API权限: {}", apiAuthorize.code());
                permission = new Permission();
                permission.setId(apiAuthorize.code());
                permission.setName(apiAuthorize.name());
                permission.setDescription(apiAuthorize.description());
                permission.setMethods(methods);
                permission.setPath(path);
                permissionService.insertSelective(permission);
            } else {
                log.info("更新API权限: {}", apiAuthorize.code());
                permission.setName(apiAuthorize.name());
                permission.setDescription(apiAuthorize.description());
                permission.setMethods(methods);
                permission.setPath(path);
                permissionService.updateSelective(permission);
            }
        });
        log.info("同步API权限完毕");
    }

}
