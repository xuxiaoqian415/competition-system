package zust.competition.sys.service;

import org.apache.ibatis.annotations.Param;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.entity.Query;

import java.util.List;

public interface TeamService {
    /**
     * 创建团队
     */
    Integer buildTeam(TeamDto teamDto);

    /**
     * 获取所有团队信息
     */
    List<TeamDto> getAllTeam();

    /**
     * 根据条件查询团队列表
     */
    List<TeamDto> searchTeam(Query query);

    /**
     * 根据id删除团队
     */
    Integer deleteTeam(Integer id);

    /**
     * 更新团队
     */
    Integer updateTeam(TeamDto teamDto);

    /**
     * 更新团队
     */
    Integer adminUpdateTeam(TeamDto teamDto);

    /**
     * 根据团队id查询团队
     */
    TeamDto getTeamById(Integer id);

    /**
     * 获取自己负责的团队
     */
    List<TeamDto> getOwnTeam(Integer id);

    /**
     * 根据compId删除原来的成员关系
     */
    Integer deleteStuCompByCompId(Integer compId);

    /**
     * 获取所有团队信息
     */
    List<TeamDto> selectTeamList(Query query);

    /**
     * 根据cpId删除团队
     */
    Integer deleteTeamByCpiD(Integer cpId);

}
