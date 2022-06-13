package zust.competition.sys.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Alias("Query")
public class Query  implements Serializable {

    private String number;

    private String password;

    private Integer type;

    private Integer id;

    private String keyword;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private String teamName;

    private Integer leaderId;

    private Integer cpId;
    /**
     * 是否评奖： 0-未评奖 1-已评奖
     */
    private Integer isAwarded;

    private Integer academyId;




}
