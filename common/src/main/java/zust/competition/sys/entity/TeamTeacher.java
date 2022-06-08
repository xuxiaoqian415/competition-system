package zust.competition.sys.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data
@Alias("TeamTeacher")
public class TeamTeacher {

    private Integer id;

    private Integer teamId;

    private Integer teacherId;

    /**
     * 状态：0-待处理 1-已拒绝 2-已同意
     */
    private Integer status;

    /**
     * 是否待定：0-有效 1-已被其他老师选择
     */
    private Integer flag;

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
