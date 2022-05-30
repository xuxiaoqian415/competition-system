package zust.competition.sys.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("Select")
public class Select {

    private Integer id;

    private Integer teamId;

    private Integer teacherId;

    private Integer selectType;//反选

    /**
     * 字段是否有效，0无效，1有效
     */
    private Integer flag;
}
