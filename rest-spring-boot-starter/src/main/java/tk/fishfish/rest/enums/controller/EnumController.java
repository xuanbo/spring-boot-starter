package tk.fishfish.rest.enums.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.fishfish.enums.EnumConstantsHolder;

/**
 * 枚举值
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@RestController
@RequestMapping("/v0/enum")
public class EnumController {

    @GetMapping("/{name}")
    public Object[] show(@PathVariable String name) {
        return EnumConstantsHolder.get(name);
    }

}
