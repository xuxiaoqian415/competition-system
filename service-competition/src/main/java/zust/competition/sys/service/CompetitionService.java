package zust.competition.sys.service;

import zust.competition.sys.dto.CompetitionDto;
import zust.competition.sys.entity.Competition;
import zust.competition.sys.entity.Query;
import zust.competition.sys.entity.UserTeam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CompetitionService {

    /**
     * 管理员发布竞赛
     */
    Integer addCompetition(Competition competition);

    /**
     * 管理员竞赛信息更新
     */
    Integer updateCompetition(CompetitionDto competitionDto);

    /**
     * 管理员获取全部竞赛列表
     */
    List<CompetitionDto> getCompetitionList();

    /**
     * 管理员根据条件查询竞赛列表
     */
    List<CompetitionDto> searchCompetition(Query query);

    /**
     * 管理员删除竞赛
     */
    Integer deleteCompetition(Integer id);

    /**
     * 管理员根据Id获取竞赛详情
     */
    CompetitionDto detail(Integer id);

    /**
     * 用户根据状态获取竞赛列表
     */
    List<CompetitionDto> getInformList(Integer status);

    /**
     * 用户根据Id获取竞赛详情
     */
    CompetitionDto getCompetitionDetail(Integer id);

    /**
     * 用户根据竞赛id获取竞赛附件路径
     */
    String getSupplement(Integer id);

    /**
     * 判断当前用户的类型
     * return：0-不可报名（教师）1-未报名过该竞赛 2-已创建团队 3-已加入团队
     */
    Integer getUserType(Integer userId, Integer cpId);

    /**
     * 下载文件
     */
    void download(String filePath, HttpServletResponse response);

}
