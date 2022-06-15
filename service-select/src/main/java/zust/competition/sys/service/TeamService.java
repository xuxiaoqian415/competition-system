package zust.competition.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import zust.competition.sys.dto.TeamDto;
import zust.competition.sys.dto.TeamTeacherDto;
import zust.competition.sys.dto.UserTeamDto;
import zust.competition.sys.dto.query.TeamQuery;
import zust.competition.sys.entity.Team;
import zust.competition.sys.entity.UserTeam;

import java.util.List;

@FeignClient("service-team")
public interface TeamService {

    @RequestMapping("/service/getTeamDto")
    TeamDto getTeamDto(TeamQuery query);

    @RequestMapping("/service/updateTeam")
    Integer updateTeam(Team team);

    @RequestMapping("/service/joinTeam")
    Integer joinTeam(UserTeamDto userTeamDto);

    @RequestMapping("/service/getLeaderTeam")
    TeamDto getLeaderTeam(@RequestParam("leaderId") Integer leaderId, @RequestParam("cpId") Integer cpId);

    @RequestMapping("/service/getInviteTeacher")
    List<String> getInviteTeacher(@RequestParam("teamId") Integer teamId);

    @RequestMapping("/service/inviteTeacher")
    Integer inviteTeacher(TeamTeacherDto dto);

    @RequestMapping("/service/requestTeam")
    List<UserTeamDto> requestTeam(@RequestParam("id") Integer id);

    @RequestMapping("/service/updateRequestStatus")
    Integer updateRequestStatus(@RequestParam("id") Integer id, @RequestParam("type") Integer type);

    @RequestMapping("/service/getUserTeam")
    UserTeam getUserTeam(@RequestParam("id") Integer id);

    @RequestMapping("/service/ownRequest")
    List<UserTeamDto> ownRequest(@RequestParam("id") Integer id);
}
