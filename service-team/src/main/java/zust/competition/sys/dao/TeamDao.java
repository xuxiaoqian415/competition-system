package zust.competition.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.entity.Message;
import zust.competition.sys.entity.Query;
import zust.competition.sys.entity.Team;
import zust.competition.sys.entity.UserTeam;

import java.util.List;

@Mapper
public interface TeamDao {
    /**
     * 加入团队
     */
    Integer insertUserTeam(UserTeam userTeam);

    /**
     * 学生发送组队申请 message
     */
    Integer studentRequest(Message message);

    /**
     * 根据邀请码查找团队信息
     */
    Team selectByCode(String invitation_code);

    /**
     * 增加团队
     */
    Integer insertTeam(Team team);

    /**
     * 根据学生id获取已创建/加入的团队
     */
    List<TeamDto> getTeamList(@Param("id") Integer id);

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

}
