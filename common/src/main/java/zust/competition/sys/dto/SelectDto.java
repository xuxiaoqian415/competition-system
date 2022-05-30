package zust.competition.sys.dto;

import lombok.Data;

@Data
public class SelectDto {
    private Integer id;

    private Integer teamId;

    private Integer teacherId;
    private String teacherName;

    /**
     * 是否被反选 0未反选 1被反选
     */
    private Integer selectType;

    /**
     * 记录是否有效 0有效 1无效
     */
    private Integer flag;

    private Integer teacher1Id;
    private Integer teacher2Id;
    private Integer teacher3Id;
}
