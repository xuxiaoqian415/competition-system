package zust.competition.sys.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Data
@Alias("TeamDto")
public class TeamDto {
    private Integer id;

    private Integer cpId;
    private String cpName;

    private String teamName;

    private Integer leaderId;
    private String leaderName;

    private Integer newLeaderId;

    private String teamIntro;

    private String member;

    private List<Integer> memberList;

    private Integer member1Id;
    private Integer member2Id;
    private Integer member3Id;
    private Integer member4Id;

    private String memberNames;
}
