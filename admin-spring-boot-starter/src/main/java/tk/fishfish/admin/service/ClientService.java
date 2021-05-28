package tk.fishfish.admin.service;

import tk.fishfish.admin.entity.Client;

/**
 * 客户端
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public interface ClientService {

    Client findById(String id);

}
