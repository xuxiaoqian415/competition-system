package zust.competition.sys.service;

import zust.competition.sys.dto.CompetitionDto;
import zust.competition.sys.entity.Competition;
import zust.competition.sys.entity.Query;
import zust.competition.sys.entity.UserTeam;

import java.util.List;

public interface CompetitionService {

    /**
     * 管理员获取竞赛列表
     */
    List<CompetitionDto> getCompetitionList();

    /**
     * 根据条件查询竞赛列表
     */
    List<CompetitionDto> searchCompetition(Query query);

    /**
     * 获取当前时间可报名竞赛
     */
    List<Competition> getCompetitionByApply();

    /**
     * 管理员发布竞赛
     */
    Integer addCompetition(Competition competition);

    /**
     * 根据Id获取竞赛详情
     */
    CompetitionDto getCompetitionDetail(Integer id);

    /**
     * 删除竞赛
     */
    Integer deleteCompetition(Integer id);

    /**
     * 竞赛更新
     */
    Integer updateCompetition(CompetitionDto competitionDto);

    /**
     * 根据学生id获取已报名竞赛
     */
    List<CompetitionDto> getApplyList(Integer id);

    /**
     * 查看学生是否已报名某竞赛
     */
    Integer ifHaveApply(UserTeam stuComp);

    /**
     * 增加一条student-competition记录
     */
    void insertUserTeam(UserTeam stuComp);

}
