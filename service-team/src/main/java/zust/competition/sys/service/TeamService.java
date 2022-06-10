package zust.competition.sys.service;

import org.apache.ibatis.annotations.Param;
import zust.competition.sys.dto.*;
import zust.competition.sys.entity.Query;
import zust.competition.sys.entity.Team;

import java.util.List;

public interface TeamService {
    /**
     * 加入团队
     */
    Integer joinTeam(UserTeamDto userTeamDto);

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


//
//    /**
//     * 创建团队
//     */
//    Integer buildTeam(TeamDto teamDto);
//
//    /**
//     * 获取所有团队信息
//     */
//    List<TeamDto> getAllTeam();
//
//    /**
//     * 根据条件查询团队列表
//     */
//    List<TeamDto> searchTeam(Query query);
//
//    /**
//     * 根据id删除团队
//     */
//    Integer deleteTeam(Integer id);
//
//    /**
//     * 更新团队
//     */
//    Integer updateTeam(TeamDto teamDto);
//
//    /**
//     * 更新团队
//     */
//    Integer adminUpdateTeam(TeamDto teamDto);
//
//    /**
//     * 根据团队id查询团队
//     */
//    TeamDto getTeamById(Integer id);
//
//    /**
//     * 获取自己负责的团队
//     */
//    List<TeamDto> getOwnTeam(Integer id);
//
//    /**
//     * 根据compId删除原来的成员关系
//     */
//    Integer deleteStuCompByCompId(Integer compId);
//
//    /**
//     * 获取所有团队信息
//     */
//    List<TeamDto> selectTeamList(Query query);
//
//    /**
//     * 根据cpId删除团队
//     */
//    Integer deleteTeamByCpiD(Integer cpId);

}
