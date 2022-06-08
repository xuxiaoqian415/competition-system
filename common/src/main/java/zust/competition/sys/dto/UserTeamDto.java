package zust.competition.sys.dto;

import lombok.Data;

import java.util.Date;
@Data
public class UserTeamDto {
    private Integer id;

    private Integer studentId;

    private Integer teamId;
    /**
     * 可担任职务
     */
    //
    private String role;
    /**
     * 状态：0-待处理1-已同意 2-已拒绝
     */
    private String status;
    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

    /**
     * 操作人id
     */
    private Integer operatorId;
}
