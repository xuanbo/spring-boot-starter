package tk.fishfish.persistence;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 实体
 *
 * @author 奔波儿灞
 * @since 1.5.0
 */
@Data
public class Entity implements Serializable {

    @Id
    private String id;

}

