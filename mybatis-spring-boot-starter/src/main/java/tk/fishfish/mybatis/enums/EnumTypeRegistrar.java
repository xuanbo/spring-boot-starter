package tk.fishfish.mybatis.enums;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 动态注册枚举类型
 *
 * @author 奔波儿灞
 * @version 1.3.0
 */
@Slf4j
public class EnumTypeRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attrs = metadata.getAnnotationAttributes(EnableEnumTypes.class.getName());
        if (attrs == null) {
            return;
        }
        Set<String> scanBasePackages = findScanBasePackages(metadata, attrs);
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathBeanDefinitionScanner(registry, false);
        scanner.setResourceLoader(this.resourceLoader);
        scanner.addIncludeFilter(new AssignableTypeFilter(EnumType.class));
        scanBasePackages.forEach(basePackage -> {
            log.info("扫描枚举类型包路径: {}", basePackage);
            scanner.findCandidateComponents(basePackage).forEach(definition -> registerEnumTypeHandler(registry, definition));
        });
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private Set<String> findScanBasePackages(AnnotationMetadata metadata, Map<String, Object> attrs) {
        Set<String> scanBasePackages = new HashSet<>();
        scanBasePackages.add(ClassUtils.getPackageName(metadata.getClassName()));
        Object value = attrs.get("value");
        if (value instanceof String[]) {
            scanBasePackages.addAll(Arrays.stream((String[]) value).map(ClassUtils::getPackageName).collect(Collectors.toList()));
        }
        Object basePackages = attrs.get("basePackages");
        if (basePackages instanceof String[]) {
            scanBasePackages.addAll(Arrays.stream((String[]) basePackages).map(ClassUtils::getPackageName).collect(Collectors.toList()));
        }
        Object basePackageClasses = attrs.get("basePackageClasses");
        if (basePackageClasses instanceof Class<?>[]) {
            scanBasePackages.addAll(Arrays.stream((Class<?>[]) basePackageClasses).map(Class::getName).map(ClassUtils::getPackageName).collect(Collectors.toList()));
        }
        return scanBasePackages;
    }

    private void registerEnumTypeHandler(BeanDefinitionRegistry registry, BeanDefinition definition) {
        String beanClassName = definition.getBeanClassName();
        if (beanClassName == null) {
            return;
        }
        try {
            Class<?> clazz = ClassUtils.forName(beanClassName, EnableEnumTypes.class.getClassLoader());
            String clazzName = clazz.getName();
            // 为枚举自动注册TypeHandler
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(EnumTypeHandler.class);
            builder.addConstructorArgValue(clazz);
            log.info("枚举 {} 自动注册TypeHandler: {}TypeHandler", clazzName, clazzName);
            registry.registerBeanDefinition(clazzName + "TypeHandler", builder.getBeanDefinition());
            EnumConstantsHolder.add(toPath(ClassUtils.getShortName(clazzName)), clazz.getEnumConstants());
        } catch (ClassNotFoundException e) {
            log.warn("无法获取枚举类型Class", e);
        }
    }

    /**
     * 大写转中划线
     *
     * @param input 大写
     * @return 转中划线
     */
    private String toPath(String input) {
        if (input == null) {
            return null;
        }
        int length = input.length();

        val sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i != 0) {
                    sb.append('-');
                }
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

}
