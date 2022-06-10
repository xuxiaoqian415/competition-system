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

//    private List<Integer> ids;

}
