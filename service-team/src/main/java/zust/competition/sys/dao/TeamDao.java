package zust.competition.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.TeamTeacherDto;
import zust.competition.sys.dto.query.TeamQuery;
import zust.competition.sys.entity.*;

import java.util.List;

@Mapper
public interface TeamDao {


    /**
     * 加入团队
     */
    Integer insertUserTeam(UserTeam userTeam);

    /**
     *  message
     */
    Integer insertMessage(Message message);

    /**
     * 根据主键查询Team
     */
    Team getTeam(TeamQuery query);

    /**
     * 根据teamId查找已邀请的老师
     */
    List<TeamTeacher> getInviteTeacher(@Param("teamId") Integer teamId);

    /**
     * 邀请老师加入 (team_teacher)
     */
    Integer insertTeamTeacher(TeamTeacher teamTeacher);

    /**
     * 查询我加入的团队
     */
    List<Team> myJoin(Integer id);

    /**
     * 获取所有团队信息
     */
    List<UserTeam> getMember(Integer id);

    /**
     * 查询我负责的团队
     */
    List<Team> ownLead(Integer id);

    /**
     * 查询我负责团队的组队申请
     */
    List<UserTeam> requestTeam(Integer id);

    /**
     * 查询我发出组队申请
     */
    List<UserTeam> ownRequest(Integer id);


    /**
     * 增加团队
     */
    Integer insertTeam(Team team);


    /**
     * 根据竞赛id和成员id查找Team
     */
    Team getMyTeamByCpId(@Param("userId") Integer userId, @Param("cpId") Integer cpId);


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
//    Integer deleteTeamByCpiD(@Param("cpId") Integer cpId);

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
    Integer deleteUserTeamByTeamId(@Param("teamId") Integer teamId);

}
