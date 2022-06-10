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

    List<TeamDto> getAllTeam();

    List<TeamDto> searchTeam(Query query);

    Integer deleteTeam(Integer id);

}
