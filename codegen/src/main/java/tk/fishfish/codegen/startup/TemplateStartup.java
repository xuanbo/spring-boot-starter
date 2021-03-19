package tk.fishfish.codegen.startup;

import jetbrick.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.zeroturnaround.zip.commons.FileUtils;
import tk.fishfish.rest.BizException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * 复制默认模板到外部文件系统，供热修改
 *
 * @author 奔波儿灞
 * @since 1.5.0
 */
@Slf4j
@Component
public class TemplateStartup implements CommandLineRunner {

    @Override
    public void run(String... args) {
        File dir = Paths.get("templates").toFile();
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                throw BizException.of(400, "templates必须是一个目录，将会放入代码模板");
            }
        } else {
            try {
                log.info("创建本地模板目录: templates");
                FileUtils.forceMkdir(dir);
            } catch (IOException e) {
                throw BizException.of(500, "创建本地模板目录失败: %s", e, e.getMessage());
            }
        }
        List<String> templates = Arrays.asList(
                "default-templates/condition.jetx",
                "default-templates/controller.jetx",
                "default-templates/repository.jetx",
                "default-templates/entity.jetx",
                "default-templates/service.jetx",
                "default-templates/serviceImpl.jetx"
        );
        for (String template : templates) {
            String name = StringUtils.removeStart(template, "default-templates/");
            File custom = Paths.get("templates", name).toFile();
            if (custom.exists()) {
                log.warn("模板 {} 已存在，跳过默认模板覆盖", custom.getPath());
                continue;
            }
            Resource resource = new ClassPathResource(template);
            try (InputStream is = resource.getInputStream()) {
                log.info("创建模板: {}", custom.getPath());
                FileCopyUtils.copy(is, new FileOutputStream(custom));
            } catch (IOException e) {
                throw BizException.of(500, "创建本地模板目录失败: %s", e, e.getMessage());
            }
        }
    }

}
