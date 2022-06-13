package zust.competition.sys.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@Data
@Alias("Team")
public class Team implements Serializable {
    private Integer id;

    private Integer cpId;

    private String teamName;

    private Integer leaderId;

    private String introduction;

    /**
     * 当前人数
     */
    private Integer nowNumber;
    /**
     * 邀请码
     */
    private String invitationCode;

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
    /**
     *  是否删除：0-未删除，1-已删除
     */
    private Integer isDelete;

    private Date createTime;

    private Date updateTime;
    //操作人id
    private Integer operatorId;

}
