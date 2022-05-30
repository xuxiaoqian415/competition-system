package zust.competition.sys.dao;

import org.apache.ibatis.annotations.Param;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.entity.Query;
import zust.competition.sys.entity.StuComp;
import zust.competition.sys.entity.Team;

import java.util.List;

public interface TeamDao {
    /**
     * 增加团队
     */
    Integer insertTeam(Team team);

    /**
     * 根据竞赛id与队长id查询团队id
     */
    Integer selectTeamId(@Param("cpId") Integer cpId, @Param("leaderId") Integer leaderId);

    /**
     * 获取所有团队信息
     */
    List<TeamDto> selectTeamList(Query query);

    /**
     * 根据id删除团队
     */
    Integer deleteTeam(@Param("id") Integer id);

    /**
     * 根据cpId删除团队
     */
    Integer deleteTeamByCpiD(@Param("cpId") Integer cpId);

    /**
     * 团队更新
     */
    Integer updateTeam(Team team);

    /**
     * 根据团队id查询团队
     */
    TeamDto selectTeamById(@Param("id") Integer id);

    /**
     * 根据teamId删除原来的成员关系
     */
    Integer deleteStuCompByTeamId(@Param("teamId") Integer teamId);

    /**
     * 根据compId删除原来的成员关系
     */
    Integer deleteStuCompByCompId(@Param("compId") Integer compId);

    /**
     * 根据stuId和竞赛id删除原来的成员关系
     */
    Integer deleteStuCompByStuId(StuComp stuComp);
}
