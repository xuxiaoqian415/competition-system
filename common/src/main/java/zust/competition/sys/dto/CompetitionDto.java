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

    /**
     * 报名起止时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyEnd;

    /**
     * 竞赛起止时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;

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

    /**
     * 竞赛状态：1-即将开始报名 2-报名进行中 3-比赛进行中 4-比赛已结束
     */
    private Integer status;

    /**
     * 用户类型：0-不可报名（教师）
     * 1-未报名过该竞赛 2-已创建团队 3-已加入团队
     */
    private Integer userType;
}
