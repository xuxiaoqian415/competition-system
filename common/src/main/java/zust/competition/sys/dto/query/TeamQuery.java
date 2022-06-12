package zust.competition.sys.dto.query;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Alias("TeamQuery")
public class TeamQuery implements Serializable {

    private Integer teamId;

    /**
     * 负责人id
     */
    private Integer leaderId;

    /**
     * 竞赛id
     */
    private Integer cpId;

    /**
     * 邀请码
     */
    private String invitationCode;
    /**
     * 获奖情况
     */
    private String result;

}
