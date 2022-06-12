package zust.competition.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zust.competition.sys.dto.CompetitionDto;
import zust.competition.sys.entity.Competition;
import zust.competition.sys.entity.Query;
import zust.competition.sys.entity.UserTeam;

import java.util.Date;
import java.util.List;

@Mapper
public interface CompetitionDao {

    /**
     * 新增一条竞赛信息
     */
    void addCompetition(Competition competition);

    /**
     * 竞赛更新
     */
    Integer updateCompetition(Competition competition);

    /**
     * 根据条件查询全部竞赛信息
     */
    List<Competition> getCompetitionList(Query query);

    /**
     * 删除竞赛
     */
    Integer deleteCompetition(@Param("id") Integer id);

    /**
     * 根据Id获取竞赛详情
     */
    Competition getCompetitionDetail(@Param("id") Integer id);









    /**
     *获取当前时间内可报名的竞赛信息
     */
    List<Competition> getCompetitionByApply(Date currentTime);

    /**
     * 增加一条student-competition记录
     */
    void insertUserTeam(UserTeam stuComp);

    /**
     * 根据学生id获取已报名竞赛
     */
    List<CompetitionDto> getApplyList(Integer id);

    /**
     * 查看学生是否已报名某竞赛
     */
    List<UserTeam> ifHaveApply(UserTeam stuComp);

}
