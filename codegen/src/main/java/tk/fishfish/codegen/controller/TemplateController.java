package tk.fishfish.codegen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.fishfish.codegen.condition.TemplateCondition;
import tk.fishfish.codegen.dto.TemplateContent;
import tk.fishfish.codegen.service.TemplateService;
import tk.fishfish.rest.execption.BizException;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 模板
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v0/template")
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping("/gen")
    public void gen(@Validated @RequestBody TemplateCondition condition, HttpServletResponse resp) {
        String name = new String("模板代码.zip".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + name + ".csv");
        try {
            templateService.gen(condition, resp.getOutputStream());
        } catch (Exception e) {
            throw BizException.of(500, "生成模板代码失败: %s", e, e.getMessage());
        }
    }

    @GetMapping("/list")
    public String[] list() {
        return new File("templates").list();
    }

    @GetMapping("/{name}")
    public TemplateContent template(@PathVariable String name) {
        Path path = Paths.get("templates", name);
        Resource resource = new FileSystemResource(path);
        if (resource.exists()) {
            try (InputStream is = resource.getInputStream()) {
                return new TemplateContent(name, new String(FileCopyUtils.copyToByteArray(is)));
            } catch (IOException e) {
                throw BizException.of(500, "获取模板内容失败: %s", e, e.getMessage());
            }
        } else {
            throw BizException.of(404, "模板不存在");
        }
    }

    @PutMapping
    public void save(@RequestBody TemplateContent templateContent) {
        Path path = Paths.get("templates", templateContent.getName());
        try {
            Files.write(path, templateContent.getContent().getBytes());
        } catch (IOException e) {
            throw BizException.of(500, "保存模板失败: %s", e, e.getMessage());
        }
    }

}
