package zust.competition.sys.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class UserTeamDto implements Serializable {
    private Integer id;

    private Integer studentId;
    /**
     * 学生名字
     */
    private String studentName;
    /**
     * 学生学院
     */
    private String academy;
    /**
     * 联系方式
     */
    private String mobile;

    private Integer teamId;

    /**
     * 请求的团队的状态：0-组队中 1-组队完成 2-报名成功
     */
    private Integer teamStatus;

    private String teamName;
    /**
     * 负责人名字
     */
    private String leaderName;
    private Integer leaderId;
    /**
     * 竞赛id
     */
    private Integer cpId;
    /**
     * 竞赛id
     */
    private String cpName;
    /**
     * 可担任职务
     */
    private String role;

    /**
     * 邀请码
     */
    private String invitationCode;

    /**
     * 状态：0-待处理 1-已同意 2-已拒绝
     */
    private Integer status;
    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

    /**
     * 操作人id
     */
    private Integer operatorId;
}
