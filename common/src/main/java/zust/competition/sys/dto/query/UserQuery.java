package zust.competition.sys.dto.query;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Data
@Alias("UserQuery")
public class UserQuery  implements Serializable {

    /**
     * 0-管理员 1-教师 2-学生
     */
    private Integer type;

    /**
     * 0-在读 1-毕业
     */
    private Integer status;

    /**
     * 所在学院
     */
    private Integer academyId;

    /**
     * 关键词（名字、学号/工号）
     */
    private String keyword;

}
