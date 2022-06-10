package zust.competition.sys.service;

import org.apache.ibatis.annotations.Param;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.UserTeamDto;
import zust.competition.sys.entity.Query;

import java.util.List;

public interface TeamService {
    /**
     * 加入团队
     */
    Integer joinTeam(UserTeamDto userTeamDto);

    /**
     * 根据团队id查询团队
     */
    TeamDto getTeamById(Integer id);

    /**
     * 获取自己负责的团队
     */
    List<TeamDto> getOwnTeam(Integer id);

    /**
     * 获取所有团队信息
     */
    List<TeamDto> selectTeamList(Query query);

    /**
     * 根据cpId删除团队
     */
    Integer deleteTeamByCpiD(Integer cpId);

    Integer adminUpdateTeam(TeamDto teamDto);
    /**
     * 负责人查看负责的已有团队
     */
    TeamDto getTeamList(Integer leaderId,Integer cpId) ;


    /**
     * 负责人向老师发送指导请求
     */
    Integer inviteTeacher(TeamTeacherDto dto);

    /**
     * 根据主键id查询团队详情
     */
    TeamDto getTeamDetail(Integer teamId) ;

    /**
     * 根据成员id查询成员详情
     */
    List<UserDto> getMember(Integer id);

    /**
     * 查询我加入的团队
     */
    List<TeamDto> myJoin(Integer id);
    /**
     * 查询我负责的团队
     */
    List<TeamDto> ownLead(Integer id);
    /**
     * 修改team
     */
   Integer updateTeam(TeamDto dto);
    /**
     * 查询我负责的团队的组队申请
     */
    List<UserTeamDto> requestTeam(Integer id);



    List<TeamDto> getAllTeam();

    List<TeamDto> searchTeam(Query query);

    Integer deleteTeam(Integer id);
}
