package zust.competition.sys.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("UserDto")
public class UserDto {
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
     * 0管理员 1教师 2学生
     */
    private Integer type;
}
