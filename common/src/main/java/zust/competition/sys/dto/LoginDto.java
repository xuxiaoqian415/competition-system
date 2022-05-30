package zust.competition.sys.dto;

import lombok.Data;

@Data
public class LoginDto {

    /**
     * 学号/工号
     */
    private String number;

    /**
     * 密码
     */
    private String password;

    /**
     * 0管理员 1教师 2学生
     */
    private Integer type;

}
