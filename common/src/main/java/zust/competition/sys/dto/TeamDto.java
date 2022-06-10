package zust.competition.sys.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.List;

@Data
@Alias("TeamDto")
public class TeamDto  implements Serializable {
    private Integer id;

    private Integer cpId;
    private String cpName;

    private String teamName;

    private Integer leaderId;
    private String leaderName;

    private Integer newLeaderId;

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

//    private String member;
//
//    private List<Integer> memberList;
//
//    private Integer member1Id;
//    private Integer member2Id;
//    private Integer member3Id;
//    private Integer member4Id;
//
//    private String memberNames;


}
