package zust.competition.sys.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.List;

@Data
@Alias("TeamDto")
public class TeamDto  implements Serializable {

//    直接对应team表
    private Integer id;
    private Integer cpId;
    private String teamName;
    private Integer leaderId;
    /**
     * 团队介绍
     */
    private String introduction;
    /**
     * 当前人数
     */
    private Integer nowNumber;
    /**
     * 邀请码
     */
    private String invitationCode;
    /**
     * 指导老师id
     */
    private Integer teacherId;
    /**
     * 是否评奖： 0-未评奖 1-已评奖
     */
    private Integer isAwarded;
    /**
     * 是否获奖：0-未获奖 1-获奖
     */
    private Integer isWin;
    /**
     * 获奖情况
     */
    private String result;
    /**
     * 状态：0-组队中 1-组队完成 2-报名成功
     */
    private Integer status;
//    直接对应team表结束

//    其他字段
    /**
     * 竞赛名称
     */
    private String cpName;
    /**
     * 负责人名字
     */
    private String leaderName;
    /**
     * 新负责人id
     */
    private Integer newLeaderId;
    /**
     * 指导老师名字
     */
    private String teacherName;
    /**
     * 已邀请的老师
     */
    private String inviteTeacher;
    /**
     * 最大人数
     */
    private Integer limitNum;
    /**
     * 是否为负责人：0-不是 1-是
     */
    private Integer isLeader;
}
