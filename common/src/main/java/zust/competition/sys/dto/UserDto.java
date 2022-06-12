package zust.competition.sys.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Data
@Alias("UserDto")
public class UserDto implements Serializable {
    private Integer id;
    private String number;
    private String name;
    private String nowpsw;  //当前密码
    private String newpsw;  //新密码
    private String rpsw;    //密码确认
    private String mobile;
    private String email;
    private String intro;
    /**
     * 学生在团队中的职位
     */
    private String role;
    /**
     * 0管理员 1教师 2学生
     */
    private Integer type;
    /**
     * 学院
     */
    private String academy;


}
