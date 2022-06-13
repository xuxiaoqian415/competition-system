package zust.competition.sys.dto.query;

import lombok.Data;

@Data
public class CountQuery {
    private Integer academyId;

    /**
     * 学院名称
     */
    private Integer academy;
    /**
     * 学院竞赛参赛情况数量
     */
    private Integer count;

    /**
     * 是否评奖： 0-未评奖 1-已评奖
     */
    private Integer isWin;
}
