package tk.fishfish.admin.dto;

import lombok.Data;

/**
 * 勾选
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
public class Select<T> {

    private boolean selected;
    private T data;

}
