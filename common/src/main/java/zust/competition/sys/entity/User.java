package zust.competition.sys.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("User")
public class User {

    private Integer id;

    private String number;

    private String name;

    private String password;

    private String mobile;

    private String email;

    private String intro;

    /**
     * 0管理员 1教师 2学生
     */
    private Integer type;

}
