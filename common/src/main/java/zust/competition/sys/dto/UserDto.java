package zust.competition.sys.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Data
@Alias("UserDto")
public class UserDto implements Serializable {

//    直接对应user表
    private Integer id;
    private String number;
    private String name;
    private String mobile;
    private String email;
    private String intro;
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
    private String academy;
    /**
     * 所在学院id
     */
    private Integer academyId;
    /**
     * 操作人id
     */
    private Integer operatorId;
//    直接对应user表结束

//    其他字段
    /**
     * 当前密码
     */
    private String nowpsw;

    /**
     * 新密码
     */
    private String newpsw;

    /**
     * 密码确认
     */
    private String rpsw;

    /**
     * 学生在团队中的职位
     */
    private String role;


}
