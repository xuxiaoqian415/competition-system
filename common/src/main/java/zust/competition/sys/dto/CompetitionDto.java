package zust.competition.sys.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Alias("CompetitionDto")
public class CompetitionDto {

    private Integer id;
    private String cpName;
    private String cpContent;

    private Integer teamId;
    private String teamName;
    private String leaderName;

    /**
     * 0未报名 1已报名
     */
    private Integer haveApply;

    /**
     * 0待处理 1老师已选
     */
    private Integer haveChoose;
    private String teacherName;

    //报名起止时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyEnd;

    //竞赛起止时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date cpStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date cpEnd;
}
