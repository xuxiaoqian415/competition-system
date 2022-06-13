package zust.competition.sys.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data
@Alias("User")
public class User {

    private Integer id;

    private String number;

    private String name;

    private String password;

    private String mobile;

    private String email;

    /**
     * 个人简介
     */
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
    private Integer academyId;
    /**
     * 所在学院
     */
    private String academy;

    /**
     * 是否删除：0-未删除 1-已删除
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 操作人id
     */
    private Integer operatorId;
}
