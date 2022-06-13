package zust.competition.sys.service;

import org.apache.ibatis.annotations.Param;
import zust.competition.sys.dto.*;
import zust.competition.sys.dto.query.CountQuery;
import zust.competition.sys.entity.Academy;
import zust.competition.sys.entity.Query;
import zust.competition.sys.entity.UserTeam;
import zust.competition.sys.entity.Team;

import java.util.List;

public interface TeamService {

    List<CountQuery> countByAcademy(CountQuery query);

    /**
     * 根据学院查询团队获奖信息
     */
    List<TeamDto> getTeamByAcademy(Integer id);
    /**
     * 模糊查询团队
     */
    List<TeamDto> searchTeamAward(Query query);
    /**
     * 根据修改时间查询团队
     */
    List<TeamDto> getTeamByTime(Integer isAwarded);

    /**
     * 未获奖操作
     */
    Integer noAwarded(Integer teamId);
    /**
     * 获奖录入
     */
    Integer updateResult(TeamDto dto);


    /**
     * 创建团队
     */
    Integer buildTeam(TeamDto dto);

    /**
     * 查看我发送的组队请求
     */
    List<UserTeamDto> ownRequest(Integer id);

    /**
     * 加入团队
     */
    Integer joinTeam(UserTeamDto userTeamDto);

    /**
     * 负责人查看某竞赛下的已有团队
     */
    TeamDto getLeaderTeam(Integer leaderId,Integer cpId) ;

    /**
     * 根据TeamId找已邀请的老师
     */
    List<String> getInviteTeacher(Integer teamId);

    /**
     * 负责人向老师发送指导请求
     */
    Integer inviteTeacher(TeamTeacherDto dto);

    /**
     * 查询我加入的团队
     */
    List<TeamDto> myJoin(Integer id);

    /**
     * 根据主键id查询团队详情
     */
    TeamDto getTeamDetail(Integer teamId);

    /**
     * 根据teamId查询成员信息
     */
    List<UserTeamDto> getMember(Integer teamId);

    /**
     * 根据teamId查询负责人信息
     */
    UserDto getLeader(Integer teamId);

    /**
     * 更新团队status
     */
    Integer updateStatus(Integer id);

    /**
     * 根据ID找UserTeam
     */
    UserTeam getUserTeam(Integer id);




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


    Integer adminUpdateTeam(TeamDto teamDto);

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

    /**
     * 更改选择记录状态
     */
    Integer updateRequestStatus(Integer id, Integer type);



    List<TeamDto> getAllTeam();

    List<TeamDto> searchTeam(Query query);

    Integer deleteTeam(Integer id);
}
