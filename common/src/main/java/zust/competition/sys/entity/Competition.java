package zust.competition.sys.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Alias("Competition")
public class Competition {

    private Integer id;

    private String title;

    private String content;

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
