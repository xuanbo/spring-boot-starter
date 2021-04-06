package tk.fishfish.admin.service;

import tk.fishfish.admin.dto.Select;
import tk.fishfish.admin.entity.Resource;

import java.util.List;

/**
 * 资源
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public interface ResourceService {

    List<Resource> tree();

    void grant(String id, List<Select<String>> selects);

}
