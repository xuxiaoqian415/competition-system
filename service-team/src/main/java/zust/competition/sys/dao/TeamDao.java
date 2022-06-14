package zust.competition.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.TeamTeacherDto;
import zust.competition.sys.dto.query.CountQuery;
import zust.competition.sys.dto.UserTeamDto;
import zust.competition.sys.dto.query.TeamQuery;
import zust.competition.sys.entity.*;

import java.util.List;

@Mapper
public interface TeamDao {

    /**
     * 统计各学院情况
     */
    List<CountQuery> countByAcademy(CountQuery query);

    /**
     * 查询所有学院
     */
    List<Academy> academyList();
    /**
     * 根据学院查询团队获奖信息
     */
    List<Team> getTeamByAcademy(Integer id);


    /**
     * 查询已评奖的团队
     */
    List<Team> searchTeamAward(TeamQuery query);

    /**
     *  message
     */
    Integer insertMessage(Message message);

    /**
     * 根据主键查询Team
     */
    TeamDto getTeam(TeamQuery query);

    /**
     * 根据ID找UserTeam
     */
    UserTeam getUserTeam(@Param("id") Integer id);

    /**
     * 修改UserTeam
     */
    Integer updateUserTeam(UserTeam userTeam);

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
     * 获取所有团队成员
     */
    List<UserTeamDto> getMember(@Param("teamId") Integer id);

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
    List<TeamDto> selectTeamList(TeamQuery query);

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
     * 某团队所有待处理请求都改为已拒绝
     */
    Integer updateUserTeamStatus(@Param("teamId") Integer teamId);

    /**
     * 根据团队id查询团队
     */
    TeamDto selectTeamById(@Param("id") Integer id);


}
