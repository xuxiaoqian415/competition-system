package zust.competition.sys.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Alias("CompetitionDto")
public class CompetitionDto {

    private Integer id;

    private String title;

    private String content;

    /**
     * 竞赛图片
     */
    private String picture;

    /**
     * 竞赛组织机构
     */
    private String organizer;

    /**
     * 附件名称
     */
    private String supplement;

    /**
     * 附件上地址
     */
    private String supplementPath;

    /**
     * 团队人员上限
     */
    private Integer limit;

    //报名起止时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyEnd;

    //竞赛起止时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date Start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date End;

    private Integer operatorId;
}
