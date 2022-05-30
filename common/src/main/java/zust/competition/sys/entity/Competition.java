package zust.competition.sys.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Alias("Competition")
public class Competition {

    private Integer id;

    private String cpName;

    private String cpContent;

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
