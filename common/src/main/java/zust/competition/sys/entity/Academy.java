package zust.competition.sys.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data
@Alias("Academy")
public class Academy {
    private Integer id;
    /**
     * 学院名称
     */
    private String name;
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
